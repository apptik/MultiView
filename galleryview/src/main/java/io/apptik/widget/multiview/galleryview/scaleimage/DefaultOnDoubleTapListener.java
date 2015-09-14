package io.apptik.widget.multiview.galleryview.scaleimage;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Provided default implementation of GestureDetector.OnDoubleTapListener, to be overriden with custom behavior, if needed
 * <p>&nbsp;</p>
 * To be used via {@link ImageViewScaler#setOnDoubleTapListener(android.view.GestureDetector.OnDoubleTapListener)}
 */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

    private ImageViewScaler ImageViewScaler;

    /**
     * Default constructor
     *
     * @param ImageViewScaler ImageViewScaler to bind to
     */
    public DefaultOnDoubleTapListener(ImageViewScaler ImageViewScaler) {
        setImageViewScaler(ImageViewScaler);
    }

    /**
     * Allows to change ImageViewScaler within range of single instance
     *
     * @param newImageViewScaler ImageViewScaler to bind to
     */
    public void setImageViewScaler(ImageViewScaler newImageViewScaler) {
        this.ImageViewScaler = newImageViewScaler;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.ImageViewScaler == null)
            return false;

        ImageView imageView = ImageViewScaler.getImageView();

        if (null != ImageViewScaler.getOnPhotoTapListener()) {
            final RectF displayRect = ImageViewScaler.getDisplayRect();

            if (null != displayRect) {
                final float x = e.getX(), y = e.getY();

                // Check to see if the user tapped on the photo
                if (displayRect.contains(x, y)) {

                    float xResult = (x - displayRect.left)
                            / displayRect.width();
                    float yResult = (y - displayRect.top)
                            / displayRect.height();

                    ImageViewScaler.getOnPhotoTapListener().onPhotoTap(imageView, xResult, yResult);
                    return true;
                }
            }
        }
        if (null != ImageViewScaler.getOnViewTapListener()) {
            ImageViewScaler.getOnViewTapListener().onViewTap(imageView, e.getX(), e.getY());
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent ev) {
        if (ImageViewScaler == null)
            return false;

        try {
            float scale = ImageViewScaler.getScale();
            float x = ev.getX();
            float y = ev.getY();

            if (scale < ImageViewScaler.getMediumScale()) {
                ImageViewScaler.setScale(ImageViewScaler.getMediumScale(), x, y, true);
            } else if (scale >= ImageViewScaler.getMediumScale() && scale < ImageViewScaler.getMaximumScale()) {
                ImageViewScaler.setScale(ImageViewScaler.getMaximumScale(), x, y, true);
            } else {
                ImageViewScaler.setScale(ImageViewScaler.getMinimumScale(), x, y, true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Can sometimes happen when getX() and getY() is called
        }

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Wait for the confirmed onDoubleTap() instead
        return false;
    }

}
