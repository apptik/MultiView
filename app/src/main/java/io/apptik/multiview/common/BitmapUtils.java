/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apptik.multiview.common;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Various bitmap utilities
 * 
 * @author alessandro
 * 
 */
public class BitmapUtils {

	private BitmapUtils() {
	}

	/**
	 * Resize a bitmap
	 * 
	 * @param input
	 * @param destWidth
	 * @param destHeight
	 * @return
	 * @throws OutOfMemoryError
	 */
	public static Bitmap resizeBitmap( final Bitmap input, int destWidth, int destHeight ) throws OutOfMemoryError {
		return resizeBitmap( input, destWidth, destHeight, 0 );
	}

	/**
	 * Resize a bitmap object to fit the passed width and height
	 * 
	 * @param input
	 *           The bitmap to be resized
	 * @param destWidth
	 *           Desired maximum width of the result bitmap
	 * @param destHeight
	 *           Desired maximum height of the result bitmap
	 * @return A new resized bitmap
	 * @throws OutOfMemoryError
	 *            if the operation exceeds the available vm memory
	 */
	public static Bitmap resizeBitmap( final Bitmap input, int destWidth, int destHeight, int rotation ) throws OutOfMemoryError {

		int dstWidth = destWidth;
		int dstHeight = destHeight;
		final int srcWidth = input.getWidth();
		final int srcHeight = input.getHeight();

		if ( rotation == 90 || rotation == 270 ) {
			dstWidth = destHeight;
			dstHeight = destWidth;
		}

		boolean needsResize = false;
		float p;
		if ( ( srcWidth > dstWidth ) || ( srcHeight > dstHeight ) ) {
			needsResize = true;
			if ( ( srcWidth > srcHeight ) && ( srcWidth > dstWidth ) ) {
				p = (float) dstWidth / (float) srcWidth;
				dstHeight = (int) ( srcHeight * p );
			} else {
				p = (float) dstHeight / (float) srcHeight;
				dstWidth = (int) ( srcWidth * p );
			}
		} else {
			dstWidth = srcWidth;
			dstHeight = srcHeight;
		}

		if ( needsResize || rotation != 0 ) {
			Bitmap output;

			if ( rotation == 0 ) {
				output = Bitmap.createScaledBitmap(input, dstWidth, dstHeight, true);
			} else {
				Matrix matrix = new Matrix();
				matrix.postScale( (float) dstWidth / srcWidth, (float) dstHeight / srcHeight );
				matrix.postRotate( rotation );
				output = Bitmap.createBitmap(input, 0, 0, srcWidth, srcHeight, matrix, true);
			}
			return output;
		} else
			return input;
	}

}
