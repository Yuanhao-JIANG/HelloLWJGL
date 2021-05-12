#version 330 core
layout (location = 0) in vec2 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoord;
layout (location = 3) in float aTexID;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoord;
out float fTexID;

void main()
{
    fColor = aColor;
    fTexCoord = aTexCoord;
    fTexID = aTexID;
    gl_Position = uProjection * uView * vec4(aPos, 0.0, 1.0);
}
