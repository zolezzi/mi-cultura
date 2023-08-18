package unq.edu.li.pdes.micultura.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Component;

@Component
public class EncodeAndDecodeCrypt {

	public String encode(String text) {
    	Encoder encoder = Base64.getEncoder();
    	String encodedString = encoder.encodeToString(text.getBytes());
    	return encodedString;
	}
	
	public String decode(String text) {
    	Decoder decoder = Base64.getDecoder();
    	byte[] bytes = decoder.decode(text);
    	return new String(bytes);
	}
}
