#version 330 core
out vec4 FragColor;

in vec3 myColor;
in vec2 texCoord;

uniform sampler2D myTexture1;
uniform sampler2D myTexture2;
uniform sampler2D myTexture3;

void main()
{
    float x = texture(myTexture2, texCoord).a;
    float y = texture(myTexture1, texCoord).a;
    //FragColor = texture(myTexture2, texCoord) * vec4(myColor, 1.0); // vec4(1.0f, 0.5f, 0.2f, 1.0f);
    FragColor = mix(texture(myTexture1, texCoord), texture(myTexture2, texCoord), 0.2 * x );
}