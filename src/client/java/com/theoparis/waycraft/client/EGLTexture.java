package com.theoparis.waycraft.client;

import org.lwjgl.opengl.GL11;

import java.io.Closeable;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.egl.KHRPlatformWayland.EGL_PLATFORM_WAYLAND_KHR;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.egl.KHRImageBase.eglCreateImageKHR;
import static org.lwjgl.egl.KHRImageBase.eglDestroyImageKHR;
import static org.lwjgl.egl.EGL15.eglGetPlatformDisplay;
import static org.lwjgl.egl.EGL15.eglInitialize;
import static org.lwjgl.egl.EGL14.EGL_DEFAULT_DISPLAY;
import static org.lwjgl.egl.EGL10.EGL_NO_CONTEXT;
import static org.lwjgl.opengles.OESEGLImage.glEGLImageTargetTexture2DOES;

public class EGLTexture implements Closeable {
    public final int id;
    public final int width;
    public final int height;

    public EGLTexture(int width, int height) {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
    }

    public void importDmaBuf(int fileDescriptor) {
        long display = eglGetPlatformDisplay(EGL_PLATFORM_WAYLAND_KHR, EGL_DEFAULT_DISPLAY, null);
        IntBuffer major = IntBuffer.allocate(1);
        IntBuffer minor = IntBuffer.allocate(1);

        eglInitialize(display, major, minor);

        IntBuffer attributeList = IntBuffer.allocate(1);

        long image = eglCreateImageKHR(display, EGL_NO_CONTEXT, GL11.GL_TEXTURE_2D, fileDescriptor, attributeList);
        glBindTexture(GL11.GL_TEXTURE_2D, id);
        glEGLImageTargetTexture2DOES(GL11.GL_TEXTURE_2D, image);

        eglDestroyImageKHR(display, image);
        glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    @Override
    public void close() throws IOException {
        glBindTexture(GL11.GL_TEXTURE_2D, id);
        glEGLImageTargetTexture2DOES(GL11.GL_TEXTURE_2D, 0);
        glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}