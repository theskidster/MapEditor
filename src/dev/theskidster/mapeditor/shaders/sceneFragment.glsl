#version 330 core

in vec3 ioColor;

uniform int uType;

out vec4 ioResult;

void main() {
    switch(uType) {
        case 0: //Used for the test triangle.
            ioResult = vec4(ioColor, 0);
            break;
    }
}