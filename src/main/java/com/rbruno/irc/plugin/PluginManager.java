package com.rbruno.irc.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;

import com.rbruno.irc.logger.Logger;

/**
 * Starts and reads plugins.
 */
public class PluginManager {

	/**
	 * Looks in plugins folder for jar files and tries to run them.
	 * 
	 * @throws IOException
	 */
	public PluginManager() throws IOException {
		File pluginFolder = new File("plugins/");
		if (!pluginFolder.exists()) pluginFolder.mkdir();

		for (String name : pluginFolder.list()) {
			if (!name.endsWith(".jar")) continue;
			File jar = new File("plugins/" + name);

			URLClassLoader loader = new URLClassLoader(new URL[] { jar.toURI().toURL() });
			URL config = loader.findResource("config.txt");
			if (config == null) {
				Logger.log(name + " was not loaded becuase config.txt was not found.", Level.FINE);
				continue;
			}
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("config.txt")));
			String main = null;
			while (inputStream.ready()) {
				String line = inputStream.readLine();
				if (line.startsWith("main") && line.split("=").length > 1) main = line.split("=")[1];
			}
			if (main == null) {
				Logger.log(name + " was not loaded because no main class was found.", Level.FINE);
				continue;
			}
			Class<?> mainClass;
			try {
				mainClass = loader.loadClass(main);
			} catch (ClassNotFoundException e) {
				Logger.log(name + " was not loaded because there was a problem loading your main class.", Level.FINE);
				continue;
			}
			Plugin plugin = null;
			try {
				plugin = (Plugin) mainClass.cast(new Plugin(name));
			} catch (ClassCastException e) {
				Logger.log(name + " was not loaded because your main class does not extend Plugin.", Level.FINE);
				continue;
			}
			if (plugin == null) {
				Logger.log(name + " was not loaded because there was a problem loading your main class.", Level.FINE);
				continue;
			}
			this.add(plugin);
			loader.close();

		}
	}

	private void add(Plugin plugin) {
		// TODO
	}

}
