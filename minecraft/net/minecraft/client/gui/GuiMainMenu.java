package net.minecraft.client.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

import com.google.common.collect.Lists;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.AnimatedButton;
import badgamesinc.hypnotic.gui.GLSLSandboxShader;
import badgamesinc.hypnotic.gui.login.GuiAltLogin;
import badgamesinc.hypnotic.module.render.Sigma;
import badgamesinc.hypnotic.util.font.FontManager;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private final GlyphPageFontRenderer bigFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 40, false, false, false);
    private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);
    public static int menuIndex = 0;
    public String shaderName = "/mainmenu" + menuIndex + ".fsh";

    public static int getMenuIndex() {
		return menuIndex;
	}
    
	public String getShaderName() {
		return shaderName;
	}

	public void setShaderName(String shaderName) {
		this.shaderName = shaderName;
	}

	// Counts the number of screen updates.
    private float updateCounter;

    // The splash message.
    private String splashText;
    private GuiButton buttonResetDemo;

    // Timer used to rotate the panorama, increases every tick.
    private int panoramaTimer;

    // Texture allocated for the current viewport of the main menu's panorama background.
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;

    // The Object object utilized as a thread lock when performing non thread-safe operations
    private final Object threadLock = new Object();

    // OpenGL graphics card warning.
    private String openGLWarning1;

    // OpenGL graphics card warning.
    private String openGLWarning2;

    // Link to the Mojang Support about minimum requirements
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    // An array of all the paths to the panorama pictures.
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;

    // Minecraft Realms button.
    private GuiButton realmsButton;

    public GuiMainMenu()
    {
        this.openGLWarning2 = field_96138_a;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try
        {
            List<String> list = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                s = s.trim();

                if (!s.isEmpty())
                {
                    list.add(s);
                }
            }

            if (!list.isEmpty())
            {
                while (true)
                {
                    this.splashText = (String)list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783)
                    {
                        break;
                    }
                }
            }
        }
        catch (IOException var12)
        {
            ;
        }
        finally
        {
            if (bufferedreader != null)
            {
                try
                {
                    bufferedreader.close();
                }
                catch (IOException var11)
                {
                    ;
                }
            }
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
        {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
        
        try {
        	if (Hypnotic.instance.saveload.dataFile.exists())
        		this.backgroundShader = new GLSLSandboxShader(shaderName);
        	else
        		this.backgroundShader = new GLSLSandboxShader("/mainmenu0.fsh");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load backgound shader", e);
        }
    }

    // Called from the main game loop to update the screen.
    public void updateScreen()
    {
        ++this.panoramaTimer;
    }

    // Returns true if this GUI should pause the game when it is displayed in single-player
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    // Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    // Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the window resizes, the buttonList is cleared beforehand.
    public void initGui()
    {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            this.splashText = "Merry X-mas!";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            this.splashText = "Happy new year!";
        }
        else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
        {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo())
        {
            this.addDemoButtons(j, 24);
        }
        else
        {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }

        ScaledResolution sr = new ScaledResolution(mc);
    	int imToLazyToGoBackAddTheSameNumberToEachThing = -27 + sr.getScaledWidth() / 10;
        this.buttonList.add(new AnimatedButton(0, this.width / 2 - 0 + imToLazyToGoBackAddTheSameNumberToEachThing, sr.getScaledHeight() - 35, 130, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new AnimatedButton(4, this.width / 2 + 150 + imToLazyToGoBackAddTheSameNumberToEachThing, sr.getScaledHeight() - 35, 130, 20, I18n.format("menu.quit", new Object[0])));
        

        synchronized (this.threadLock)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.func_181537_a(false);
        
        initTime = System.currentTimeMillis();
    }

    // Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
    	ScaledResolution sr = new ScaledResolution(mc);
    	int imToLazyToGoBackAddTheSameNumberToEachThing = -27 + sr.getScaledWidth() / 10;
        this.buttonList.add(new AnimatedButton(1, this.width / 2 - 450 + imToLazyToGoBackAddTheSameNumberToEachThing, sr.getScaledHeight() - 35, 130, 20, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new AnimatedButton(2, this.width / 2 - 300 + imToLazyToGoBackAddTheSameNumberToEachThing, sr.getScaledHeight() - 35, 130, 20, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new AnimatedButton(14, this.width / 2 - 150 + imToLazyToGoBackAddTheSameNumberToEachThing, sr.getScaledHeight() - 35, 130, 20, "Alt Manager"));
        this.buttonList.add(new AnimatedButton(16, this.width - 104, 4, 100, 20, "Cycle BG"));
    }

    // Adds Demo buttons on Main Menu for players who are playing Demo.
    private void addDemoButtons(int p_73972_1_, int p_73972_2_)
    {
        this.buttonList.add(new AnimatedButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new AnimatedButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null)
        {
            this.buttonResetDemo.enabled = false;
        }
    }

    //Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 14)
        {
            this.mc.displayGuiScreen(Hypnotic.instance.guiAltLogin);
        }

        if (button.id == 4)
        {
            this.mc.shutdown();
        }
        
        if (button.id == 16) {
        	Hypnotic.instance.saveload.save();
        	System.out.println("Loaded " + shaderName.replace("/", ""));
        	try {
        		if (menuIndex < 6) {
        			menuIndex++;
        			shaderName = "/mainmenu" + menuIndex + ".fsh";
        			this.backgroundShader = new GLSLSandboxShader(shaderName);
        		} else {
        			shaderName = "/mainmenu" + 0 + ".fsh";
        			menuIndex = 0;
        		}     
                
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load backgound shader", e);
            }
        }

        if (button.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    private void switchToRealms()
    {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.openGLWarningLink)});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }


    // Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	ScaledResolution sr = new ScaledResolution(mc);
    	
    	
    	//mc.getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/Background.png"));
    	//Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
    	GlStateManager.enableAlpha();
        GlStateManager.disableCull();
        int i = 274;
        int j = this.width / 2 - i / 2;
        int k = 30;
        
        this.backgroundShader.useShader(this.width, this.height, mouseX, mouseY, (System.currentTimeMillis() - initTime) / 1000f);
        
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);

        GL11.glEnd();

        GL20.glUseProgram(0);
        
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        
        Gui.drawRect(0, sr.getScaledHeight() - 55, sr.getScaledWidth(), sr.getScaledHeight() - 58, -1);
        Gui.drawRect(0, sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight() - 55, new Color(0, 0, 0, 160).getRGB());
        
        // Changelog
        ArrayList<String> additions = new ArrayList();
        ArrayList<String> changes = new ArrayList();
        ArrayList<String> removals = new ArrayList();
        
        //additions
        additions.add("Added AutoDisable to KillAura and Speed");
        additions.add("Added LowHop mode to speed");
        additions.add("Added NCP mode for step");   
        additions.add("Added 2D mode for ESP");  
        additions.add("Added friend system");
        additions.add("Added FakePlayer");
        additions.add("Added Keystrokes");
        additions.add("Added Freecam");
        additions.add("Added Blink");					
        additions.add("Added Glint");
        additions.add("Added Wings");
        additions.add("Sigma");
        
        //changes
        changes.add("Fixed KillAura rotations");
        changes.add("Fixed alt manager crashes");
        changes.add("Changed button animation");
        changes.add("Improved Notifications");
        changes.add("Various GUI improvements");
        changes.add("Fixed not being able to use items with NoSlow enabled");
        
        // removals
        removals.add("Removed boxed mode in the Array List due to bugs");

        bigFontRenderer.drawString("Changes", 4, 4, -1, true);
        
        int addCount = 32;
        
        for (String addtion : additions) {
            fontRenderer.drawString("§a+§f " + addtion, 16, addCount, -1, true);
            addCount += 10;
        }
        
        int changeCount = addCount;
        
        for (String change : changes) {
            fontRenderer.drawString("§e*§f " + change, 16, changeCount, -1, true);
            changeCount += 10;
        }
        
        int removeCount = changeCount;
        
        for (String removeal : removals) {
            fontRenderer.drawString("§c-§f " + removeal, 16, removeCount, -1, true);
            removeCount += 10;
        }
        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(new ResourceLocation(Hypnotic.instance.moduleManager.getModule(Sigma.class).isEnabled() ? "hypnotic/textures/sigma/sigma2.png" : "hypnotic/textures/MainMenu/Hypnotic.png"));
        Gui.drawScaledCustomSizeModalRect(sr.getScaledWidth() / 3, sr.getScaledHeight() / 3, 0, 0, 679, 300, 679 / 2, 300 / 2, 679, 300);
        //FontManager.hypnoticFontLarge.drawCenteredString("Hypnotic", this.width / 2, this.height / 2, -1);
        String s1 = "Copyright Mojang AB. Do not distribute!";
        //fontRenderer.drawString(s, 2, this.height - 14, -1, true);
        //fontRenderer.drawString(s1, this.width - this.fontRendererObj.getStringWidth(s1) + 16, this.height - 14, -1, true);
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0)
        {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    // Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock)
        {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}
