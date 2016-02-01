#ifdef GL_ES 
precision mediump float;
#endif

uniform vec3 u_color;

varying vec2 v_textCoord;
uniform sampler2D u_texture;

void main() {
  //  gl_FragColor = vec4(u_color, 1.0);
    gl_FragColor = vec4(1,0,1,1);

    
}
