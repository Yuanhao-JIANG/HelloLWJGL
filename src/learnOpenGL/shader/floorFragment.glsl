#version 330 core
out vec4 FragColor;

in vec3 myColor;
in vec2 texCoord;

uniform sampler2D floorTexture;

void main()
{
    FragColor = texture(floorTexture, texCoord);// * vec4(myColor, 0.7); // vec4(1.0f, 0.5f, 0.2f, 1.0f);
}