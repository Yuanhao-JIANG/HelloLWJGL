#version 330 core
in vec4 fColor;
in vec2 fTexCoord;
in float fTexID;

uniform sampler2D uTextures[8];

out vec4 color;

void main() {
    //float noise = fract(sin(dot(fColor.xy ,vec2(12.9898,78.233))) * 43758.5453);
    //color = fColor * noise;
    int texUnitIndex = int(fTexID) - 1;
    if (int(fTexID) != 0) {
        color = fColor * texture(uTextures[texUnitIndex], fTexCoord);
        //color = vec4(fTexCoord ,0,1);
    } else {
        color = fColor;
    }
}