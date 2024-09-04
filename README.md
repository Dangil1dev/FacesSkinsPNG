# FaceSkin App

FaceSkin is a Java application designed to streamline the process of combining multiple character face images into a single PNG file with a transparent background. This tool is particularly useful for game developers or modders who need to extract and manage character face sprites without resorting to repetitive manual editing in Photoshop.

## Features
- **Face Selection**: Allows users to select up to seven face images from their file system.
- **Automated Merging**: Combines selected face images into a single PNG file with a transparent background, maintaining the original image quality.
- **User-Friendly Interface**: Built with Java Swing, the app provides an easy-to-use graphical interface for selecting and viewing face images.
- **Directory Navigation**: Remembers the last directory used for image selection, making it convenient to work with files from the same source.

## How It Works
1. **Select Faces**: Users can choose up to seven face images through a file chooser dialog. The selected images are displayed in a scrollable panel within the app.
2. **Automatic Merge**: Once the face limit is reached, the app automatically merges the images side by side into a single PNG file.
3. **File Saving**: The final merged image is saved in the directory of the last selected face image, with a filename `faceskin.png`.

## Usage
1. Run the app.
2. Click the "Choose face" button to start selecting images.
3. After selecting seven images, the app will automatically merge them and save the final PNG file in the appropriate directory.

## Example
```bash
java -jar FaceSkin.jar

Installation
To run the app, you need to have Java installed. Clone the repository and compile the Java files:

git clone https://github.com/Dangil1dev/FacesSkinsPNG.git
cd FacesSkinsPNG
javac FacesSkin.java
java seilaporra.FacesSkin
