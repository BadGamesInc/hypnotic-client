#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

const float color_depth = 256.0;

float rand(vec2 co){
	return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

float dither(float channel, float rng) {
	float v = floor(channel * color_depth);
 	float fr = fract(channel * color_depth);
	if (fr > rng) {
		v += 1.0;
	}
	return (v / color_depth);
}

float smooth_rand(float x, float offset){
	float v = x + offset;
	return (sin(v) + sin(v/3.145232) + sin(v/13.435345) + sin(v/103.432423)) / 8.0 + 0.5;
}

float wrap(float x) {
	float v = fract(x/2.0);
	if (v < 0.5)
		return v*2.0;
	return (1.0 - v) * 2.0;
}

vec4 dither(vec4 color, float rng) {
	return vec4( dither(color.x, rng), dither(color.y, rng), dither(color.z, rng), dither(color.w, rng) );
}

float sqr_blend(float A, float B, float x) {
	return sqrt(A*A*(1.0-wrap(x)) + B*B*wrap(x));
}

vec3 sqr_blend(vec3 A, vec3 B, float x) {
	return vec3( sqr_blend(A.x, B.x, x), sqr_blend(A.y, B.y, x), sqr_blend(A.z, B.z, x) );
}

void main( void ) {

	vec2 position = ( gl_FragCoord.xy / resolution.xy );

	float dither_rng = rand(gl_FragCoord.xy);

	vec3 cA = vec3( smooth_rand(time, 100.0), smooth_rand(time, 1000.0), smooth_rand(time, 10000.0) );
	vec3 cB = vec3( smooth_rand(time, 10100.0), smooth_rand(time, 11000.0), smooth_rand(time, 20000.0) );
	
	float grad_angle = smooth_rand(time/20.0, 0.0) * 3.141593 * 2.0;
	vec2 grad = vec2( sin(grad_angle), cos(grad_angle) );
	vec2 distort = vec2( smooth_rand(position.x*10.0, time), smooth_rand(position.y*10.0, -time) )*1.0;
	
	gl_FragColor = dither(vec4( sqr_blend(cA, cB, grad.x*position.x*distort.x + grad.y*position.y*distort.y), 1.0 ), dither_rng);

}