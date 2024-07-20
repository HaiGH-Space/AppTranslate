package model;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;
import translation.BingTranslator;
import translation.enity.TranslationParams;
import translation.enity.TranslationResult;
import translation.exception.TranslationConfigLoadException;
import translation.exception.TranslationException;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class APITranslate {
    static Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("localhost", 80));
    static OkHttpClient httpClient = new OkHttpClient().newBuilder()
//            .proxy(proxy)
            .build();
    static BingTranslator translator = new BingTranslator(httpClient);
    static TranslationParams params = TranslationParams.builder().build();
    public static void config(@Nullable String from, String to){
        if (from == null) {
            from = "auto-detect";
        }
        params.setFromLang(from);
        params.setToLang(to);
    }
    public static String translate(String text){
        try {
            params.setText(text);
            TranslationResult result = translator.translate(params);
            return result.getTranslation();
        }

        catch (TranslationException | TranslationConfigLoadException e) {
            e.printStackTrace();
        } finally {
            translator.close();
        }
        return "Error translating text";
    }
}
