package badgamesinc.hypnotic.util;

public class StringValue extends Value<String> {

    public StringValue(String label, String value) {
        super(label, value);
    }

    public StringValue(String label, String value,Value parentValueObject,String parentValue) {
        super(label, value,parentValueObject,parentValue);
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
