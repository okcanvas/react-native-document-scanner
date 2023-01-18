package com.okcanvas.documentscanner.helpers;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;


public class ScannedDocument {

    public Mat original;
    public Mat processed;
    public Quadrilateral quadrilateral;
    public Point[] previewPoints;
    public Size previewSize;
    public Size originalSize;

    public Point[] originalPoints;

    public double ratio = 1;
    public int heightWithRatio;
    public int widthWithRatio;

    public ScannedDocument(Mat original) {
        this.original = original;
    }

    public Mat getProcessed() {
        return processed;
    }

    public ScannedDocument setProcessed(Mat processed) {
        this.processed = processed;
        return this;
    }

    public WritableMap previewPointsAsHash() {
        if (this.previewPoints == null) return null;
        WritableMap rectangleCoordinates = new WritableNativeMap();
        
        WritableMap topLeft = new WritableNativeMap();
        topLeft.putInt("x", Double.valueOf(this.originalPoints[0].x * this.ratio).intValue());
        topLeft.putInt("y", Double.valueOf(this.originalPoints[0].y * this.ratio).intValue());

        WritableMap topRight = new WritableNativeMap();
        topRight.putInt("x", Double.valueOf(this.originalPoints[1].x * this.ratio).intValue());
        topRight.putInt("y", Double.valueOf(this.originalPoints[1].y * this.ratio).intValue());

        WritableMap bottomRight = new WritableNativeMap();
        bottomRight.putInt("x", Double.valueOf(this.originalPoints[2].x * this.ratio).intValue());
        bottomRight.putInt("y", Double.valueOf(this.originalPoints[2].y * this.ratio).intValue());

        WritableMap bottomLeft = new WritableNativeMap();
        bottomLeft.putInt("x", Double.valueOf(this.originalPoints[3].x * this.ratio).intValue());
        bottomLeft.putInt("y", Double.valueOf(this.originalPoints[3].y * this.ratio).intValue());

        rectangleCoordinates.putMap("topLeft", topLeft);
        rectangleCoordinates.putMap("topRight", topRight);
        rectangleCoordinates.putMap("bottomRight", bottomRight);
        rectangleCoordinates.putMap("bottomLeft", bottomLeft);

        return rectangleCoordinates;
    }

    public void release() {
        if (processed != null) {
            processed.release();
        }
        if (original != null) {
            original.release();
        }

        if (quadrilateral != null && quadrilateral.contour != null) {
            quadrilateral.contour.release();
        }
    }
}
