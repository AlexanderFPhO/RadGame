#version 330

in vec2 outTextCoord;
uniform int selected;
out vec4 fragColor;

struct Material
{
    vec4 diffuse;
};

uniform sampler2D txtSampler;
uniform Material material;

void main()
{
    fragColor = texture(txtSampler, outTextCoord) + material.diffuse;
    if (selected > 0) {
            fragColor = vec4(fragColor.x, fragColor.y, 1, 1);
        }
}