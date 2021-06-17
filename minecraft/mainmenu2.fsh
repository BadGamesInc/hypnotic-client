#ifdef GL_ES
precision mediump float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //
// neon circuit by it7c 

const float PI  = 3.141592653589793;
const float PI2 = PI* 2.;

#define saturate(x) clamp(x,0.,1.)

float tri(float x){return abs(2.*fract(x*.5-.25)-1.)*2.-1.;}
float sqr(float x){return -2.*(step(.5,fract(x*.5))-.5);}
float circuit(float x){return clamp(tri(x*4.)*.25,(sqr(x)-1.)*.5,(sqr(x+.5)+1.)*.5);}
float circuit2(float x){return clamp(tri(x*.5)*.5,0.,.25);}
float flow(float x,float d){return (1.+sin(x+d*iTime*5.+3.))*.3+.3;}
vec2 pp(vec2 p){float r=.5/p.y;return vec2(p.x*r, r);}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
 float t=floor(iTime*30.)/100.;
 vec2 p = (fragCoord.xy * 2.0 - iResolution.xy) / min(iResolution.x, iResolution.y);
 float l=0.,d=sign(p.y);
 vec2 r = p;
 vec2 q = p;
 q=pp(q);
 float n = floor(q.x/.9);
 q.x=mod(q.x,.5)-.1;
 p=pp(p);
 l+=step(abs(q.x+circuit(q.y+sign(p.y)*t+n)),.005);
 l+=pow(.001/abs(p.x+circuit(p.y+d*t)),flow(p.y,d));
 l+=pow(.001/abs(p.x+.75+circuit2(p.y+d*t)),(1.+sin(p.y+d*iTime*5.))*.3+.3);
 l+=pow(.001/abs(p.x-.1+circuit2(p.y+d*t)),(1.+sin(p.y+d*iTime*5.))*.3+.3);
 l=saturate(l);
 l*=abs(r.y*2.);
 l*=abs(sin(r.y*200.+sign(p.y)*iTime*5.))*.5+.5;
 l+=pow(.001/abs(p.x+circuit(p.x+d*iTime)),(1.+sin(p.x+d*iTime*5.+3.))*.3+.3);
 fragColor = vec4(vec3(l)-vec3(1.0,11.9,.7), 1.0);
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}