# Image Processing
This project focuses on the manipulation of images as a matrix of triplets of values (red, green, blue). Basic Filters, Matrix Convolutions, and Matrix Transformations are used to output filtered versions of these images.

## Storing Images and Basic Filters

The class used for manipulation of images is simply called the `Image` class. The constructor is rarely used outside of the class, as it takes as an argument a three dimensional array. Instead, use `Image.open(filename)` to return a new Image object. As I am not exactly sure how to read binary image files from scratch, I instead used the `BufferedImage` class to open the images, and then read all red, green, and blue values of each pixel into an instance variable: a matrix accessible with the `rgbPixels()` accessor method.

```java
String filename = "image.jpeg";
Image img = Image.open(filename);
Utilities.printArray(img.rgbPixels());
```
---

We can then perform some simple operations on our matrix, by iteration through each pixel:
- Converting to Negative: each value is equal to 255 - value.
```java
Image negative = img.toNegative();
```
- Converting to Grayscale: each value is equal to the average of all 3 values in the pixel.
```java
Image grayscale = img.toGrayscale();
```
- Converting to Black and White: each value is equal to 0 if under a threshold (set by default to half of 255), 255 otherwise.
```java
Image bw = img.toBW(threshold);
```
---

We can then finally save the manipulated file to a directory.
```java
img.saveAs(String filename, String dir);
```

## Insertion and Resizing

The next algorithms I designed were insertion and resizing algorithms, as to truly design hellscapes.

The insertion function is pretty simple: rewrite the pixels in the first image with those in the second image. There are also startRow and startCol optional arguments as to center an image, for example.
```java
Image img1 = Image.open(filename1);
Image img2 = Image.open(filename2);

Image inserted1 = img1.insert(img2);
Image inserted2 = img2.insert(img1, 10, 20);
```
I had some more trouble with the resizing algorithm, as to me it is pretty unsatisfying code to have very similar code repeated across four conditions that made sure whether to reference x to find newx, or reverse engineer it (otherwise, much more data would be lost).
```
Image img = Image.open(filename1);
Image resized = img.resize(50, 50);
```

## Matrix Convolutions
Until now, image manipulation for filters has been majoritarily altering one element at a time. But for more interesting ones, usage of matrix convolution is necessary. A convolution is defined as the action of a matrix upon an image (often 3x3), where you multiply the neighbors of each element with its equivalent in the affecting matrix. There is also a version of the function which repeats the convolution a given number of times. The general function is:
```java
Image img = Image.open(filename);
Image convoluted = img.applyConvolution(matrix);
Image convoluted2 = img.applyConvolution(matrix, 100);
```
---

However, the `Image` class provides several static matrices as examples:
- Blur: takes the average of surrounding pixels for the element.
```java
Image blurred = img.applyConvolution(Image.blur);
```
- Gaussian Blur: similar to the previous, but with a more uneven distribution.
```java
Image gaussBlurred = img.applyConvolution(Image.blurGaussian);
```
- Edges: there are three versions of this matrix, but it allows for borders to be put forward.
```java
Image edges = img.applyConvolution(Image.edges);
```
- Stamped: honestly I don't even really know what this one does.
```java
Image stamped = img.applyConvolution(Image.stamped);
```
- Sharpen: applies the opposite of the blurring convolution.
```java
Image sharpen = img.applyConvolution(Image.stamped);
```

## Matrix Transformations

Matrix transformations do not modify pixels, as with convolutions, but rather find a new coordinate for each of them. The general function is:
```java
Image img = Image.open(filename);
Image transformed = img.applyTransformation(matrix);
```

Similar to convolutions, the `Image` class provides built-in static matrices:
- Incline: "inclined" images are often just a random 2x2 matrix
```java
Image inclined = img.applyTransformation(Image.incline1);
```
- Dilatation: this is a resizing algorithm which has a kx and a ky as input
```java
Image dilatated = img.applyTransformation(Image.dilatation(2, 0.5));
```
- Rotation: this allows the image to be rotated (input given in radians)
```java
Image rotated = img.applyTransformation(Image.rotation(Math.PI / 3));
```