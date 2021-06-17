
#extension GL_OES_standard_derivatives : enable

#ifdef GL_ES
precision mediump float;
#endif
uniform float time;
uniform vec2 resolution;

float iTime = 0.;
#define iResolution resolution
#define pi acos(-1.)
#define rot(a) mat2(cos(a),-sin(a),sin(a),cos(a))

#define iTime (iTime + 120.)*2.

float pModPolar(inout vec2 p, float repetitions) {
	float angle = 2.*pi/repetitions;
	float a = atan(p.y, p.x) + angle/2.;
	float r = length(p);
	float c = floor(a/angle);
	a = mod(a,angle) - angle/2.;
	p = vec2(cos(a), sin(a))*r;
	// For an odd number of repetitions, fix cell index of the cell in -x direction
	// (cell index would be e.g. -5 and 5 in the two halves of the cell):
	if (abs(c) >= (repetitions/2.)) c = abs(c);
	return c;
}

float sdSegment( in vec2 p, in vec2 a, in vec2 b )
{
    vec2 pa = p-a, ba = b-a;
    float h = clamp( dot(pa,ba)/dot(ba,ba), 0.0, 1.0 );
    return length( pa - ba*h );
}

float opSmoothUnion( float d1, float d2, float k ) {
    float h = clamp( 0.5 + 0.5*(d2-d1)/k, 0.0, 1.0 );
    return mix( d2, d1, h ) - k*h*(1.0-h); }

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = (fragCoord - 0.5*iResolution.xy)/iResolution.y;
    float pxSz = fwidth(uv.y);
    
    vec3 col = vec3(0);
    
    float sunH =  + sin(iTime*0.4)*0.1;
    
    float sunM = sunH*4. + 0.4;
    col = mix(vec3(0.4,0.23,0.1 + sunM*0.)*4.4,vec3(0.2 - sunH*0.6,0.3+ sunH*0.4,0.5)*1.,smoothstep(0.,1.,uv.y*0.8 +0.5));
    
    {
        vec2 p = uv - vec2(0.1,+0.1+sunH*1.4);
        float d = length(p) - 0.2;
        float sh = smoothstep(0.5,0.0,abs(d));
        
        #define smm(a)  sin(a + sin(a))*0.05

        col = mix(col, col*0.5*vec3(1,1,0.) + vec3(1. ,0.5,0.5)+ sin(d*2.)*sh * vec3(0.6,0.5,1.)*1.,smoothstep(fwidth(d),0.,d - 0.1 + smm(iTime))*0.4);
        col = mix(col, col*0.5*vec3(1,1,0.) + vec3(1. ,0.6,0.5)+ sin(d*4.)*sh * vec3(0.6,0.5,1.)*1.,smoothstep(fwidth(d),0.,d - 0.2 + smm(iTime + 0.4))*0.12);
        
        col = mix(col,vec3(1. ,0.6,0.5)*1.5+ 1.*sin(-d*2.)*sh * vec3(0.6,0.5,1.)*1.,smoothstep(fwidth(d),0.,d+ smm(iTime + 0.1)*0.6));
        
        {
            p *= 1. + smm(iTime)*3.;
            float sm = sin(iTime)*0.02;
            //p *= rot(sm*14. + iTime + sin(iTime) + pi);
            //p *= rot( (iTime + sin(iTime))*float(mod(iTime,pi*8.)<pi*2.));
            //p*= rot(sin(iTime)*float(mod(iTime,pi) < pi));
            p*= rot((sin(iTime))*smoothstep(-0.5,1.,sin((iTime/2. + pi/2.))));
            
            
            d = sdSegment( p - vec2(0,-0.05 + sin(p.x/0.1*pi/3. - pi/2.)*0.05), vec2(-0.1 + sm,0.),vec2(0.05+sm,0.) )-0.01;
            p.x = abs(p.x);
            p.x -= 0.15;
            d = min(d,length(p)-0.03);
            
            col = mix(col,col*vec3(0.5,0.3,0.3)*2.,smoothstep(fwidth(d),0.,d));
        
        }
    }
    {
        for(float cl = 0.; cl < 14.; cl++){
        
            float sc = 1.2;
            float sl = 0.02*iTime*(1. + sin(cl*20.)*0.9);
            float slid = floor(sl);
            vec2 p = uv + vec2(0. + mod(sl,1.)*4. - 2.,sin(slid*15. + cl)*0.2 - 0.4);;
            p *= sc;
            p.x *= 0.9;
            p *= rot(-0.32);
            float d = 10e5;
            //d = min(d, sdSegment( p + vec2(0,0.1), vec2(-0.1,0.), vec2(0.2,0. )) - 0.05);
            for(float i = 0.; i < 2.; i++){
                vec2 q = p;
                float rad = 0.1 + sin(i)*0.04;
                float rep = 2.;
                float id = pModPolar(q, rep);
                d = min(d, (length(q) - rad)/sc);    
            
                d = min(d,(length(q - vec2(0.1,0.)) - rad*0.5 - sin(id*20. + iTime + cos(id)*0.)*0.05*rad)/sc); 
                p *= rot(-0.05 + cl*11. + sin(i + cos(cl))*0.4);
                p -= vec2(0.1,0.);
             }
             vec2 dd = vec2(dFdx(d),dFdy(d));
             
             float sh = smoothstep(0.1,0.0,abs(d))*0.1;
             
             //col = mix(col,col*1.*vec3(0.4,0.5,1),smoothstep(0.1,0.,d)*0.2);
             
             //col = mix(col,vec3(1.,0.59,0.44 + sin(uv.y*2. + d*10.)*0.1)*1.5,smoothstep(fwidth(d),0.,d)*0.8);
             /*
             col = mix(col,vec3(
                 0.7 + sin(dd.x*21. + dd.y*10.)*4.*sh,
                 0.2- sin(-dd.y*20.)*1.*smoothstep(0.0,0.0,abs(d)),
                 0.34)*0.44,smoothstep(fwidth(d),0.,d));
             */
        }
        
    }
    
    {
        for(float cl = 0.; cl < 54.; cl++){
        
            float sc = 1.2;
            float sl = 0.05*iTime*(1. + sin(cl*25.1)*0.2);
            float slid = floor(sl);
            vec2 p = uv + vec2(0. + mod(sl,1.)*4. - 2.,sin(slid*15. + cl)*0.2 - 0.3);;
            p *= sc;
            //p.x *= 0.9;
            float d = 10e5;
            //d = length(p) - 0.02;
            float w = 0.02; float ww = 0.005;
            //p.x = abs(p.x);
            float m =  sin(cl + iTime*4.)*0.3;
            float mm =  sin(cl + iTime*4. - 2.4)*0.3;
            
            p.y -= mm*0.1;
            p *= rot(m);
            
            d = min(d,sdSegment( p, vec2(0.0,0.), vec2(w,0.) ) - ww);
            p *= rot(-m*2.- pi);
            
            d = opSmoothUnion( d, sdSegment( p, vec2(0,0.), vec2(w,0.) ) - ww, 0.01 );

            
            //for(float i = 0.; i < 2.; i++){
                //p.x = -p.x;
                
            
            //}
            
            vec2 dd = vec2(dFdx(d),dFdy(d));
            
            
            float sh = smoothstep(0.1,0.0,(d))*0.1;
            col = mix(col,0.25+0.5*col*vec3(0.4,0.6,0.9)*1.,smoothstep(fwidth(d),0.,d));
        
        }
        
    }
    
    {
        for(float cl = 0.; cl < 14.; cl++){
        
            float sc = 1.2;
            float sl = 0.02*iTime*(1. + sin(cl*204.124)*0.5);
            float slid = floor(sl);
            vec2 p = uv + vec2(0. + mod(sl,1.)*4. - 2.,sin(slid*15. + cl)*0.2 - 0.4);;
            p *= sc;
            //p.x *= 0.9;
            //p *= rot(-0.32);
            float d = 10e5;
            d = min(d, sdSegment( p + vec2(0,0.), vec2(-0.1,0.), vec2(0.2,0. )) - 0.05);
            for(float i = 0.; i < 3.5; i++){
                if (i > 1.5 + sin(cl)*2.) break;
                p -= vec2(0.,0.1);
             
                vec2 q = p;
                float cd =  sdSegment( p, vec2(-0.+ sin(i + cl*20.)*0.4,0.), vec2(0.2+ sin(i*20. + cl*4.)*0.4,0. )) - 0.05;
                
                d = opSmoothUnion(d, cd, 0.05 );

                //d = min(d,);
                
            }
            
            //col = mix(col,col*1.*vec3(0.4,0.5,1),smoothstep(0.1,0.,d)*0.2);
            col = mix(col,vec3(1.,0.59,0.44 + sin(uv.y*2. + d*10.)*0.1)*1.5,smoothstep(fwidth(d),0.,d)*0.8);
             
            /*
             col = mix(col,vec3(
                 0.7 + sin(dd.x*21. + dd.y*10.)*4.*sh,
                 0.2- sin(-dd.y*20.)*1.*smoothstep(0.0,0.0,abs(d)),
                 0.34)*0.44,smoothstep(fwidth(d),0.,d));
             */
        }
        
    }
    {
       for(float cl = 0.; cl < 14.; cl++){
        
            /*
             col = mix(col,vec3(
                 1. + smoothstep(fwidth(d),0.,d - dd.x*4. - 2.*dd.y)*0. ,
                 0.59,
                 0.44 + sin(uv.y*2. + d*10.)*0.1)*1.5,smoothstep(fwidth(d),0.,d)*0.8);
             */
             /*
             col = mix(col,vec3(
                 0.7 + sin(dd.x*21. + dd.y*10.)*4.*sh,
                 0.2- sin(-dd.y*20.)*1.*smoothstep(0.0,0.0,abs(d)),
                 0.34)*0.44,smoothstep(fwidth(d),0.,d));
             */
        }
    }
    
    
    {
        #define pmod(p,a) mod(p,a) - 0.5*a
        
        const float cnt = 19.;
        float h = 0.7 ;
        #define tsin(a) mix(sin(a),asin(sin(a)),0.8)
            
        {
            vec2 p = uv + vec2(0,-0.3 + h);
            float tm = p.x*3.  + iTime*0.15;
            
            
            //#define geth(tm) (tsin(tm + tsin(tm*1.)))*0.1 - 0.2 + tsin(tm*5.)*0.02
            #define geth(tm) ((sin(tm + sin(tm*1.)))*0.1 - 0.2 + sin(tm*5.)*0.02)
            
            //float d = p.y + (tsin(tm*2. + tsin(tm*3.)*0.5 + cos( + tm*2.)*2.)*0.1 + tsin(tm*1.5)*0.2)*0.4 - 0.3;
            
            float d = p.y + geth(tm);
            
            for(float i = 0.; i <4.; i++){
                p = uv + vec2(0,-0.3 + h);
            
                float md = 0.4 + sin(i)*0.1;
                float id = floor(tm/md)*md + 0.5*md;

                float hh = geth(id);

                p.x = pmod(tm,md);
                p.x /= 3.;
                float s = - 0.024 + sin(id*20. + i*20.)*0.01;
                d = min(d, length(p + vec2(0,hh + 0.01)) +s);
            
            }
            
            
            
            col = mix(col,vec3(0.2 ,0.4 + sin(d*1.5)*0.4,0.34)*1.4,smoothstep(fwidth(d),0.,d));
            
        }
        for(float terr = 0.; terr < cnt; terr++){
            float terrIdx = terr/cnt;
            vec2 p = uv + vec2(0,0.3 + terrIdx*h);
            
            float tm = p.x*1. + terrIdx + iTime*(1. + terrIdx*0.)*.1 ;
            tm += sin(tm*2. + terrIdx*8.)*1.4;
            #define gethb(tm) (sin(tm*1.+ terrIdx*18. + sin(tm*3.)*0.5 + cos( + tm*2.+ terrIdx*5.)*2.)*0.1 + sin(tm*1.5 + terrIdx*5.)*0.2)*0.24

            float d = p.y + gethb(tm);
            //float d = p.y + (sin(tm*2. + tsin(tm*3.)*0.5 + cos( + tm*2.)*2.)*0.1 + tsin(tm*1.5)*0.2)*0.4;
            
            if(terr>=0.){
                vec2 q = p;
                //float tm = p.x*3.  + iTime*0.15;
            
            
                //#define geth(tm) (tsin(tm + tsin(tm*1.)))*0.1 - 0.2 + tsin(tm*5.)*0.02
                //#define geth(tm) ((sin(tm + sin(tm*1.)))*0.1 - 0.2 + sin(tm*5.)*0.02)

                //float d = p.y + (tsin(tm*2. + tsin(tm*3.)*0.5 + cos( + tm*2.)*2.)*0.1 + tsin(tm*1.5)*0.2)*0.4 - 0.3;

                float db = p.y + gethb(tm);
                
                
                
                for(float i = 0.; i <1.; i++){
                    p = uv + vec2(0,0.3 + terrIdx*h);
                    float ltm = tm + sin(i + terr*20.);
                    float md = 0.2 + sin(i+ terr*0.3)*0.5;
                    float id = floor(ltm/md)*md + 0.5*md;

                    float hh = gethb(id);
                    
                    p.x = pmod(ltm,md);
                    p.x += sin(id)*md*0.1;
                    
                    
                    float s = - 0.034 + sin(id*20. + i*20.)*0.03;
                    p += vec2(0,hh );
                    db = min(db, length(p) +s);
                
                    for(float j = 0.; j < 0.; j++){
                        vec2 q = p;
                        float rep = 1. + floor(sin(id + i*20.)*2.4);
                        float idb = pModPolar(q, rep);
                        q.x -= 0.02 + sin(idb*15. + j*20.)*0.035;
                        p *= rot(2.5 + sin(j + i));
                        db = min(db, length(q + vec2(0,0))-0.028 + sin(idb + id)*0.0);
                
                    }
                    //db = min(db, length(p + vec2(0,hh -0.1)) +s);
                    p=q;
                }
                
                //col = mix(col,0.2*vec3(0.5 + sin(terrIdx*14.)*0.4,0.4 + sin(d*2.5)*0.4,0.34 + sin(d*4.5)*0.2)*1.4,smoothstep(fwidth(d),0.,db));
                //col = mix(col,vec3(0.3 + sin(terrIdx*24.)*0.1 ,0.4 + sin(d*1.5)*0.4,0.34)*1.4,smoothstep(fwidth(db),0.,db));
                
                //col = mix(col,vec3(0.5 + sin(terrIdx*24.)*0.3,0.4 + sin(d*2.5)*0.2,0.34 + sin(d*4.5)*0.2)*1.4,smoothstep(fwidth(db),0.,db));
            
                
            }
            
            col = mix(col,vec3(0.5 + sin(terrIdx*24.)*0.3,0.4 + sin(d*2.5)*0.2,0.34 + sin(d*4.5)*0.2)*1.4,smoothstep(fwidth(d),0.,d));
            
        }
        
        /*
        vec2 p = uv + vec2(0,0.2);
            
        float tm = p.x*2. + iTime*(1.)*.2 ;
            //tm += sin(tm*2.)*1.6;
        p.y = p.y + (sin(tm*2. + sin(tm*3.)*0.5 + cos( + tm*2.)*2.)*0.1 + sin(tm*1.5)*0.2)*0.1 - 0.2;
        
        
        float md = 1.;
        float id = floor(p.x/md);
        p.x += iTime + sin(iTime);
        p.x = mod(p.x,md) - 0.5*md;
        
        float  d = sdSegment( p + vec2(sin(iTime+md)*0.1,0.), vec2(-0.2,0.), vec2(0.2,0.)) - 0.01;
        */
        /*
        col = mix(col,vec3(
                 1. ,
                 0.59,
                 0.44 + sin(uv.y*2. + d*10.)*0.1)*1.5,smoothstep(fwidth(d),0.,d)*0.8);
          */   
    }
    
    //col = vec3(1.,0.98,1.) - col*vec3(.5,0.8,1.);
    
    
    
    //col *= vec3(1,0.9,1.15);
    col = pow(col,vec3(1.1,1.15,1.2));
    col = pow(col,vec3(0.4545));
    fragColor = vec4(col,1.0);
}

#undef iTime

void main(void)
{
    iTime = time;
    mainImage(gl_FragColor, gl_FragCoord.xy);
}