#include <cstring>
#include <wayland-server.h>
#include <wayland-client.h>
#include <jni.h>
#include <EGL/egl.h>

// Wayland compositor that renders to minecraft window entities
// It should provide a way to handle wayland surface creation and event handling
// And it should have a way to send the dmabuf buffer file descriptor to the java renderer which uses eglCreateImageKHR to create an EGLImage

class WaylandCompositor {
public:
    WaylandCompositor() {
        display = wl_display_create();
        registry = wl_display_get_registry(display);
        wl_registry_add_listener(registry, &registry_listener, this);
        wl_display_dispatch(display);
        wl_display_roundtrip(display);
    }

    void run() {
        while (wl_display_dispatch(display) != -1) {
            // do nothing
        }
    }

    static void registry_handle_global(void *data, struct wl_registry *registry, uint32_t name, const char *interface, uint32_t version) {
        WaylandCompositor *compositor = static_cast<WaylandCompositor *>(data);
        if (strcmp(interface, "wl_compositor") == 0) {
            compositor->compositor = static_cast<wl_compositor *>(wl_registry_bind(registry, name, &wl_compositor_interface, 1));
        }
    }

    static void registry_handle_global_remove(void *data, struct wl_registry *registry, uint32_t name) {
        // do nothing
    }

    static const struct wl_registry_listener registry_listener;

private:
    wl_display *display;
    wl_registry *registry;
    wl_compositor *compositor;
};

