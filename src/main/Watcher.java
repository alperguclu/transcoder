package main;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.util.NoSuchElementException;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Watcher {
	private final WatchService watcher;
	private final Path dir;
	private final Converter converter;

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	public Watcher(String directoryPath) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dir = Paths.get(directoryPath);
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
		this.converter = new Converter();
	}

	void processEvents() {
		while (true) {
			WatchKey key;

			try {
				key = watcher.take();

				for (WatchEvent<?> event : key.pollEvents()) {
					Kind<?> kind = event.kind();

					WatchEvent<Path> ev = cast(event);
					Path name = ev.context();

					if (kind == ENTRY_CREATE) {
						if (name.toString().contains(".wav")) {
							converter.q.add(name.toString());
						}
					}

					if (kind == ENTRY_DELETE) {
						if (name.toString().contains(".wav")) {
							converter.q.remove(name.toString());
						}
					}

					System.out.format("%s: %s\n", event.kind().name(), name);
					key.reset();
				}

			} catch (InterruptedException x) {
				x.printStackTrace();
			}

			if (converter.q.size() > 0) {
				try {
					for (int i = 0; i < converter.q.size(); i++) {
						String sourcePath = converter.q.pop();
						String targetPath = sourcePath.replace("wav", "mp3");
						converter.WAVToMP3(sourcePath, targetPath);
					}
				} catch (NoSuchElementException ex) {
					ex.printStackTrace();
				}
			}

		}
	}
}
