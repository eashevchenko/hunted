attribute vec4 a_position;
uniform mat4 u_worldView; 
attribute vec4 a_Color;         // Per-vertex color information we will pass in.
 
varying vec4 v_Color; 

void main()
{
 v_Color = a_Color;
gl_Position =  u_worldView * a_position;
}      