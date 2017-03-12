/*
 *  Copyright 2016 Jeroen Mols
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.jmolsmobile.landscapevideocapture.configuration;

import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;

import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureQuality;
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureResolution;

public class CaptureConfiguration implements Parcelable {

    public static final int MBYTE_TO_BYTE = 1024 * 1024;
    public static final int MSEC_TO_SEC = 1000;

    public static final int NO_DURATION_LIMIT = -1;
    public static final int NO_FILESIZE_LIMIT = -1;

    private int mVideoWidth = PredefinedCaptureConfigurations.WIDTH_720P;
    private int mVideoHeight = PredefinedCaptureConfigurations.HEIGHT_720P;
    private int mBitrate = PredefinedCaptureConfigurations.BITRATE_HQ_720P;
    private int mMaxDurationMs = NO_DURATION_LIMIT;
    private int mMaxFilesizeBytes = NO_FILESIZE_LIMIT;
    private boolean mShowTimer = false;
    private boolean mAllowFrontFacingCamera = true;
    private int mVideoFPS = PredefinedCaptureConfigurations.FPS_30;     //Default FPS is 30.

    private int OUTPUT_FORMAT = MediaRecorder.OutputFormat.MPEG_4;
    private int AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT;
    private int AUDIO_ENCODER = MediaRecorder.AudioEncoder.AAC;
    private int VIDEO_SOURCE = MediaRecorder.VideoSource.CAMERA;
    private int VIDEO_ENCODER = MediaRecorder.VideoEncoder.H264;

    public CaptureConfiguration() {
        // Default configuration
    }

    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality) {
        mVideoWidth = resolution.width;
        mVideoHeight = resolution.height;
        mBitrate = resolution.getBitrate(quality);
    }

    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality, int maxDurationSecs,
                                int maxFilesizeMb, boolean showTimer) {
        this(resolution, quality, maxDurationSecs, maxFilesizeMb, showTimer, false);
        mShowTimer = showTimer;
    }

    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality, int maxDurationSecs,
                                int maxFilesizeMb, boolean showTimer, boolean allowFrontFacingCamera) {
        this(resolution, quality, maxDurationSecs, maxFilesizeMb);
        mShowTimer = showTimer;
        mAllowFrontFacingCamera = allowFrontFacingCamera;
    }

    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality, int maxDurationSecs,
                                int maxFilesizeMb, boolean showTimer, boolean allowFrontFacingCamera,
                                int videoFPS) {
        this(resolution, quality, maxDurationSecs, maxFilesizeMb, showTimer, allowFrontFacingCamera);
        mVideoFPS = videoFPS;
    }

    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality, int maxDurationSecs,
                                int maxFilesizeMb) {
        this(resolution, quality);
        mMaxDurationMs = maxDurationSecs * MSEC_TO_SEC;
        mMaxFilesizeBytes = maxFilesizeMb * MBYTE_TO_BYTE;
    }

    public CaptureConfiguration(int videoWidth, int videoHeight, int bitrate) {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
        mBitrate = bitrate;
    }

    public CaptureConfiguration(int videoWidth, int videoHeight, int bitrate, int maxDurationSecs, int maxFilesizeMb) {
        this(videoWidth, videoHeight, bitrate);
        mMaxDurationMs = maxDurationSecs * MSEC_TO_SEC;
        mMaxFilesizeBytes = maxFilesizeMb * MBYTE_TO_BYTE;
    }

    /**
     * @return Width of the captured video in pixels
     */
    public int getVideoWidth() {
        return mVideoWidth;
    }

    /**
     * @return Height of the captured video in pixels
     */
    public int getVideoHeight() {
        return mVideoHeight;
    }

    /**
     * @return Bitrate of the captured video in bits per second
     */
    public int getVideoBitrate() {
        return mBitrate;
    }

    /**
     * @return Maximum duration of the captured video in milliseconds
     */
    public int getMaxCaptureDuration() {
        return mMaxDurationMs;
    }

    /**
     * @return Maximum filesize of the captured video in bytes
     */
    public int getMaxCaptureFileSize() {
        return mMaxFilesizeBytes;
    }

    /**
     * @return If timer must be displayed during video capture
     */
    public boolean getShowTimer() {
        return mShowTimer;
    }

    /**
     * @return If front facing camera toggle must be displayed before capturing video
     */
    public boolean getAllowFrontFacingCamera() {
        return mAllowFrontFacingCamera;
    }

    public int getOutputFormat() {
        return OUTPUT_FORMAT;
    }

    public int getAudioSource() {
        return AUDIO_SOURCE;
    }

    public int getAudioEncoder() {
        return AUDIO_ENCODER;
    }

    public int getVideoSource() {
        return VIDEO_SOURCE;
    }

    public int getVideoEncoder() {
        return VIDEO_ENCODER;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mVideoWidth);
        dest.writeInt(mVideoHeight);
        dest.writeInt(mBitrate);
        dest.writeInt(mMaxDurationMs);
        dest.writeInt(mMaxFilesizeBytes);
        dest.writeInt(mVideoFPS);
        dest.writeByte((byte) (mShowTimer ? 1 : 0));
        dest.writeByte((byte) (mAllowFrontFacingCamera ? 1 : 0));

        dest.writeInt(OUTPUT_FORMAT);
        dest.writeInt(AUDIO_SOURCE);
        dest.writeInt(AUDIO_ENCODER);
        dest.writeInt(VIDEO_SOURCE);
        dest.writeInt(VIDEO_ENCODER);
    }

    public static final Parcelable.Creator<CaptureConfiguration> CREATOR = new Parcelable.Creator<CaptureConfiguration>() {
        @Override
        public CaptureConfiguration createFromParcel(
                Parcel in) {
            return new CaptureConfiguration(in);
        }

        @Override
        public CaptureConfiguration[] newArray(
                int size) {
            return new CaptureConfiguration[size];
        }
    };

    private CaptureConfiguration(Parcel in) {
        mVideoWidth = in.readInt();
        mVideoHeight = in.readInt();
        mBitrate = in.readInt();
        mMaxDurationMs = in.readInt();
        mMaxFilesizeBytes = in.readInt();
        mVideoFPS = in.readInt();
        mShowTimer = in.readByte() != 0;
        mAllowFrontFacingCamera = in.readByte() != 0;

        OUTPUT_FORMAT = in.readInt();
        AUDIO_SOURCE = in.readInt();
        AUDIO_ENCODER = in.readInt();
        VIDEO_SOURCE = in.readInt();
        VIDEO_ENCODER = in.readInt();
    }

    public int getVideoFPS() {
        return mVideoFPS;
    }

    public static class Builder {

        private final CaptureConfiguration configuration;

        public Builder(CaptureResolution resolution, CaptureQuality quality) {
            configuration = new CaptureConfiguration();
            configuration.mVideoWidth = resolution.width;
            configuration.mVideoHeight = resolution.height;
            configuration.mBitrate = resolution.getBitrate(quality);
        }

        public Builder(int width, int height, int bitrate) {
            configuration = new CaptureConfiguration();
            configuration.mVideoWidth = width;
            configuration.mVideoHeight = height;
            configuration.mBitrate = bitrate;
        }

        public CaptureConfiguration build() {
            return configuration;
        }

        public Builder maxDuration(int maxDurationSec) {
            configuration.mMaxDurationMs = maxDurationSec * MSEC_TO_SEC;
            return this;
        }

        public Builder maxFileSize(int maxFileSize) {
            configuration.mMaxFilesizeBytes = maxFileSize * MBYTE_TO_BYTE;
            return this;
        }

        public Builder frameRate(int frameRate) {
            configuration.mVideoFPS = frameRate;
            return this;
        }

        public Builder showTimer() {
            configuration.mShowTimer = true;
            return this;
        }

        public Builder noCameraToggle() {
            configuration.mAllowFrontFacingCamera = false;
            return this;
        }
    }
}