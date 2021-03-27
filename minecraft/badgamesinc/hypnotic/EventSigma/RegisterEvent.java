package badgamesinc.hypnotic.EventSigma;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterEvent {

    public Class<? extends Event>[] events();

}
