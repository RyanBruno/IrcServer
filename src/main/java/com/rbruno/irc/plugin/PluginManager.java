package com.rbruno.irc.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.logging.Level;

import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.templates.Request;

/**
 * Starts and reads plugins.
 */
public class PluginManager {

	private ArrayList<Plugin> plugins = new ArrayList<Plugin>();

	/**
	 * Looks in plugins folder for jar files and tries to run them.
	 * 
	 * @throws IOException
	 */
	public PluginManager() throws IOException {
		File pluginFolder = new File("plugins/");
		if (!pluginFolder.exists()) pluginFolder.mkdir();

		for (String path : pluginFolder.list()) {
			if (!path.endsWith(".jar")) continue;
			File jar = new File("plugins/" + path);

			URLClassLoader loader = new URLClassLoader(new URL[] { jar.toURI().toURL() });
			URL config = loader.findResource("plugin.txt");
			if (config == null) {
				Logger.log(path + " was not loaded becuase plugin.txt was not found.", Level.FINE);
				continue;
			}
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("plugin.txt")));
			String main = null;
			String name = null;
			while (inputStream.ready()) {
				String line = inputStream.readLine();
				Logger.log(line);
				if (line.startsWith("main") && line.split("=").length > 1) main = line.split("=")[1];
				if (line.startsWith("name") && line.split("=").length > 1) name = line.split("=")[1];
			}
			if (main == null) {
				Logger.log(path + " was not loaded because no main class was found.", Level.FINE);
				continue;
			}
			if (name == null) {
				Logger.log(path + " was not loaded because no plugin name was found.", Level.FINE);
				continue;
			}
			if (this.getPlugin(name) != null) {
				Logger.log(path + " was not loaded because a plugin with the same name is already loaded.", Level.FINE);
				continue;
			}
			Class<?> mainClass;
			try {
				mainClass = loader.loadClass(main);
			} catch (ClassNotFoundException e) {
				Logger.log(path + " was not loaded because there was a problem loading your main class.", Level.FINE);
				continue;
			}
			Plugin plugin = null;
			Logger.log(mainClass.toString(), Level.FINE);
			try {
				plugin = (Plugin) mainClass.newInstance();
			} catch (ClassCastException e) {
				Logger.log(path + " was not loaded because your main class does not extend Plugin.", Level.FINE);
				continue;
			} catch (InstantiationException e) {
				Logger.log(path + " was not loaded because your main class does not extend Plugin.", Level.FINE);
				continue;
			} catch (IllegalAccessException e) {
				Logger.log(path + " was not loaded because your main class does not extend Plugin.", Level.FINE);
				continue;
			}
			if (plugin == null) {
				Logger.log(path + " was not loaded because there was a problem loading your main class.", Level.FINE);
				continue;
			}
			this.add(plugin, name);
			loader.close();

		}
	}

	private void add(Plugin plugin, String name) {
		plugin.setName(name);
		plugin.onEnable();
		plugins.add(plugin);
	}

	public Plugin getPlugin(String name) {
		for (Plugin current : getPlugins())
			if (current.getName().equals(name)) return current;
		return null;
	}

	public void runOnRequest(Request request) {
		for (Plugin current : getPlugins()) {
			try {
				current.onRequest(request);
			} catch (Exception e) {
				Logger.log("An error occured while passing a request to a plugin.", Level.FINE);
				e.printStackTrace();
			}

		}
	}

	public ArrayList<Plugin> getPlugins() {
		return new ArrayList<Plugin>(plugins);
	}

}
