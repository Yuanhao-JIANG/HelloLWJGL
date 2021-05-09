#version 330 core
in vec4 fColor;
in vec2 textCoord;

uniform float uTime;
uniform sampler2D tex;

out vec4 color;

void main() {
    //float noise = fract(sin(dot(fColor.xy ,vec2(12.9898,78.233))) * 43758.5453);
    //color = fColor * noise;
    color = texture(tex, textCoord);
}