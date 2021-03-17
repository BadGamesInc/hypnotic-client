package badgamesinc.hypnotic.util.font;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class FontManager {

    private ResourceLocation darrow = new ResourceLocation("SF-UI-Display-Regular.otf");

    private FontUtils defaultFont;

    public FontManager getInstance() {
        return instance;
    }

    public FontUtils getFont(String key) {
        return fonts.getOrDefault(key, defaultFont);
    }

    private FontManager instance;

    private HashMap<String, FontUtils> fonts = new HashMap<>();

    public FontManager() {
        instance = this;
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<>();
        defaultFont = new FontUtils(executorService, textureQueue, new Font("Verdana", Font.PLAIN, 18));
        try {
            for (int i : new int[]{6, 7, 8, 10, 11, 12, 14}) {
                InputStream istream = getClass().getResourceAsStream("/assets/minecraft/SF-UI-Display-Regular.otf");
                Font myFont = Font.createFont(Font.PLAIN, istream);
                myFont = myFont.deriveFont(Font.PLAIN, i);
                fonts.put("SFR " + i, new FontUtils(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{6, 7, 8,9, 11}) {
                InputStream istream = getClass().getResourceAsStream("/assets/minecraft/SF-UI-Display-Bold.otf");
                Font myFont = Font.createFont(Font.PLAIN, istream);
                myFont = myFont.deriveFont(Font.PLAIN, i);
                fonts.put("SFB " + i, new FontUtils(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{6, 7, 8, 9, 11, 12}) {
                InputStream istream = getClass().getResourceAsStream("/assets/minecraft/SF-UI-Display-Medium.otf");
                Font myFont = Font.createFont(Font.PLAIN, istream);
                myFont = myFont.deriveFont(Font.PLAIN, i);
                fonts.put("SFM " + i, new FontUtils(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{17, 10, 9, 8, 7, 6}) {
                InputStream istream = getClass().getResourceAsStream("/assets/minecraft/SF-UI-Display-Light.otf");
                Font myFont = Font.createFont(Font.PLAIN, istream);
                myFont = myFont.deriveFont(Font.PLAIN, i);
                fonts.put("SFL " + i, new FontUtils(executorService, textureQueue, myFont));
            }
            for (int i : new int[]{19}) {
                InputStream istream = getClass().getResourceAsStream("/assets/minecraft/Jigsaw-Regular.otf");
                Font myFont = Font.createFont(Font.PLAIN, istream);
                myFont = myFont.deriveFont(Font.PLAIN, i);
                fonts.put("JIGR " + i, new FontUtils(executorService, textureQueue, myFont));
            }
            fonts.put("Verdana 12", new FontUtils(executorService, textureQueue, new Font("Verdana", Font.PLAIN, 12)));

            fonts.put("Verdana Bold 16", new FontUtils(executorService, textureQueue, new Font("Verdana Bold", Font.PLAIN, 16)));
            fonts.put("Verdana Bold 20", new FontUtils(executorService, textureQueue, new Font("Verdana Bold", Font.PLAIN, 20)));
        } catch (Exception ignored) {

        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                TextureData textureData = textureQueue.poll();
                GlStateManager.bindTexture(textureData.getTextureId());

                // Sets the texture parameter stuff.
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                // Uploads the texture to opengl.
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());
            }
        }
    }
}
