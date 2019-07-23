package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class eXtensionController extends Application {

	@FXML
	public Label debugLabel;

	@FXML
	public TextField fromExtension;

	@FXML
	public TextField toExtension;

	@FXML
	public Label directoryLabel;

	File selectedDirectory = null;

	public void run() {

		if (fromExtension != null && fromExtension.getText().length() != 0 && toExtension != null
				&& toExtension.getText().length() != 0) {
			if (selectedDirectory != null) {
				alterExtension(selectedDirectory, fromExtension, toExtension);

			} else {
				debugLabel.setText("Error, please choose a folder!");
			}
		} else {
			debugLabel.setText("Error, please insert file extensions!");
		}

	}

	public void chosenDirectory() {

		DirectoryChooser directory = new DirectoryChooser();
		selectedDirectory = directory.showDialog(null);

		if (selectedDirectory != null) {

			directoryLabel.setText(selectedDirectory.getAbsolutePath());
		}

	}

	private void alterExtension(File selectedDirectory, TextField fromExtension, TextField toExtension) {

		String newDirectory = selectedDirectory.getAbsolutePath() + "/" + toExtension.getText() + "/";

		if (new File(newDirectory).mkdirs() == true) {
			System.out.println("Created successfully!");
		} else {
			System.out.println("Directory already exists!");
		}

		for (File file : selectedDirectory.listFiles()) {
			if (file.getName().endsWith(fromExtension.getText())) {
				String extension = file.getName().substring(file.getName().lastIndexOf(".")).trim();
				extension = extension.substring(extension.lastIndexOf(".") + 1);
				// System.out.println(extension);

				String newExtension = toExtension.getText();

				String newFileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + "." + newExtension;
				System.out.println(newFileName);

				File dest = new File(newDirectory + newFileName);

				try {
					copyFileUsingStream(file, dest);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
