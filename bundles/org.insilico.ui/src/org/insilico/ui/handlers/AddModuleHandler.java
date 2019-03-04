package org.insilico.ui.handlers;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.insilico.ui.Constants;
import org.insilico.ui.utils.DialogUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * Desired to integrate some Module(Maven Sub-Project) into inSilico Platform.
 * 
 * To add additional module compatibility criteria checks extend
 * checkSelectedDir()
 * 
 * The parent project must have its packaging option pre-configured to pom, for
 * a module to be integrated and bundled with it.
 * 
 * To ensure the parent project has this option set correctly before proceeding,
 * check if the pom.xml file of your parent project has set packaging parameter
 * correctly.
 * 
 */
public abstract class AddModuleHandler {
	@Inject
	DialogUtils dialogUtils;

	@Inject
	IEclipseContext context;

	/**
	 * Copy module to the target location in platform
	 * 
	 * @param src path of a module to be copied
	 */
	public abstract void copyModule(Path src);

	@Execute
	void init(Stage primaryStage) {
		DirectoryChooser dc = new DirectoryChooser();
		File file = dc.showDialog(primaryStage);
		if (file != null) {
			if (checkSelectedDir(file.toPath())) {
				copyModule(file.toPath());
				dialogUtils.showConfirmationDialog("Add Module", null,
						"Integration process is finished. inSilico should be restarted to apply the new settings. Do you want to restart the application?",
						AlertType.CONFIRMATION, (v) -> {
							if (v) {
								IWorkbench workbench = context.get(IWorkbench.class);
								workbench.restart();
							}
						});
			} else {
				dialogUtils.showConfirmationDialog("Add Module", "Integration process can not be finished",
						"Selected folder contains uncompatible data", AlertType.ERROR);
			}
		}
	}

	/**
	 * Get absolute path to the platform
	 */
	protected String getAbsolutePlatformPath() {
		return getAbsoluteCorePluginPath().concat("../../");
	}

	/**
	 * Get absolute path to inSilico Core plug-in
	 */
	protected String getAbsoluteCorePluginPath() {
		return Platform.getBundle(Constants.INSILICO_CORE_ID).getLocation().replace("reference:file:", "");
	}

	/**
	 * Concatenates the specified string to the end of the absolute path of the
	 * platform.
	 */
	protected Path concatToPlatformPath(String path) {
		return new File(getAbsolutePlatformPath().concat(path)).toPath();
	}

	/**
	 * Copy source folder to the target destination recursively. Replaces existing
	 * files.
	 * 
	 * @throws IOException
	 * 
	 */
	public void copyFolder(Path src, Path dest) {
		try {
			Path newDest = Paths.get(dest.toString(), src.getFileName().toString());
			if (!newDest.toFile().exists()) {
				Files.createDirectory(newDest);
			}
			Files.walk(src).forEach(source -> {
				copy(source, newDest.resolve(src.relativize(source)));
			});
			extendPomWithModule(dest.toString().concat("/pom.xml"), src.getFileName().toString());

		} catch (IOException e) {
			dialogUtils.showConfirmationDialog("Add Module", "Integration process can not be finished", e.getMessage(),
					AlertType.ERROR, e);
		}
	}

	/**
	 * Copy source file to the target destination recursively. Replaces existing
	 * file if it exists.
	 * 
	 */
	private void copy(Path source, Path dest) {
		try {
			Files.copy(source, dest, REPLACE_EXISTING);
		} catch (Exception e) {
			dialogUtils.showConfirmationDialog("Add Module", "Integration process can not be finished", e.getMessage(),
					AlertType.ERROR, e);
		}
	}

	/**
	 * Extend pom.xml with specified entry
	 * 
	 * @param moduleId module id to add
	 * 
	 */
	private void extendPomWithModule(String dest, String moduleId) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(dest);
			Element module = doc.createElement("module");
			module.appendChild(doc.createTextNode(moduleId));
			doc.getElementsByTagName("modules").item(0).appendChild(module);
			saveDocModelToFile(dest, doc);
		} catch (Exception e) {
			dialogUtils.showConfirmationDialog("Add Module", "Integration process can not be finished", e.getMessage(),
					AlertType.ERROR);
		}
	}

	/**
	 * Convert DOM Document to destination file
	 * 
	 * @param doc    DOM Document to convert
	 * @param target destination file to write to
	 */
	private void saveDocModelToFile(String target, Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(target));
		transformer.transform(source, result);
	}

	/**
	 * Check if target module has a pom.xml file
	 */
	private boolean checkSelectedDir(Path target) {
		Path pom = new File(target.toFile(), "/pom.xml").toPath();
		return Files.exists(pom, NOFOLLOW_LINKS);
	}
}
