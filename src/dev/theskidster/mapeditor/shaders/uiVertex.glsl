#version 330 core

layout (location = 0) in vec2 aPosition;
layout (location = 1) in vec2 aTexCoords;

uniform int  uType;
uniform mat4 uProjection;

out vec2 ioTexCoords;

void main() {
    switch(uType) {
        case 0: //Used for text rendering.
            ioTexCoords = aTexCoords;
            gl_Position = uProjection * vec4(aPosition, 0, 1);
            break;
    }
}