#version 330 core

in vec2 ioTexCoords;

uniform int uType;
uniform vec3 uColor;
uniform sampler2D uTexture;

out vec4 ioResult;

void main() {
    switch(uType) {
        case 0: //Used for rendering text.
            ioResult = vec4(uColor, texture(uTexture, ioTexCoords).a);
            break;

        case 1: //Used for drawing background rectangles.
            ioResult = vec4(uColor, 0);
            break;
    }
}