package badgamesinc.hypnotic.gui.altmanager;


import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.AnimatedButton;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiAltManager extends GuiScreen {
    private GuiButton login;
    private GuiButton remove;
    private GuiButton rename;
    private GuiScreen previousScreen;
    private AltLoginThread loginThread;
    private int offset;
    public Alt selectedAlt;
    private String status;
    private static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 20, false, false, false);


    public GuiAltManager(GuiScreen previousScreen) {
    	this.previousScreen = previousScreen;
        this.selectedAlt = null;
        this.status = EnumChatFormatting.GRAY + "Idle...";
    }

    public void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {

                break;
            }
            case 1: {
                (this.loginThread = new AltLoginThread(selectedAlt)).start();
                break;
            }
            case 2: {
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                AltManager.registry.remove(this.selectedAlt);
                this.status = "§aRemoved.";
                try {
                    Hypnotic.instance.getFileManager().getFile(Alts.class).saveFile();
                } catch (Exception ex) {
                }
                this.selectedAlt = null;
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            }
            case 5: {
                final ArrayList<Alt> registry = AltManager.registry;
                if (registry.size() != 0) {
	                final Random random = new Random();
	                final Alt randomAlt = registry.get(random.nextInt(AltManager.registry.size()));
	                (this.loginThread = new AltLoginThread(randomAlt)).start();
                }
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiRenameAlt(this));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(previousScreen);
                break;
            }
            case 8: {
                AltManager.registry.clear();
                try {
                    Hypnotic.instance.getFileManager().getFile(Alts.class).loadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.status = "§bReloaded!";
                break;
            }
            case 9:{
                mc.displayGuiScreen(new GuiTheAltening(this));
                break;
            }
            case 10:
                mc.displayGuiScreen(new GuiImportFile());
                break;
        }
    }

    private ResourceLocation background = new ResourceLocation("hypnotic/textures/Alt.png");
    int lastOffset = 0;
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
    	this.drawDefaultBackground();
    	Gui.drawRect(this.width, this.height, 0, 0, new Color(0, 0, 0, 200).getRGB());
        if (Mouse.hasWheel()) {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (wheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        
        if(lastOffset != offset){
            int diff = offset - lastOffset;
            lastOffset += diff / 4;
        }
        ScaledResolution res = new ScaledResolution(mc);
        int w = res.getScaledWidth();
        int h = res.getScaledHeight();
        mc.getTextureManager().bindTexture(background);
        //drawScaledCustomSizeModalRect(0, 0, 0, 0, w + 2, h, w + 2, h, w + 2, h);
        GlStateManager.bindTexture(0);
        //this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);

        RenderUtils.rectangle(0, 0, res.getScaledWidth(), res.getScaledHeight(), Colors.getColor(0, 50));
        //DEBUGGING
        mc.fontRendererObj.drawString("", -10, 10, -1);
        fontRenderer.drawString(this.mc.session.getUsername(), 10, 10, 14540253, true);
        final StringBuilder sb = new StringBuilder("Alt Manager - ");
        String altAm = AltManager.registry.size() < 2 ? "alt" : "alts";
        fontRenderer.drawCenteredString( sb.append(AltManager.registry.size()).append(" " + altAm).toString(), this.width / 2, 5, -1, true);
        fontRenderer.drawCenteredString( (this.loginThread == null) ? this.status : this.loginThread.getStatus(), this.width / 2, 15, -1, true);
        Gui.drawRect(50.0f, 33.0f, this.width - 50, this.height - 60, Colors.getColor(225, 50));
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, this.width, this.height - 50);
        GL11.glEnable(3089);
        int y = 38;
        for (final Alt alt : getAlts()) {
            if (isAltInArea(y)) {
                String name;
                if (alt.getMask().equals("")) {
                    name = alt.getUsername();
                } else {
                    name = alt.getMask();
                }
                String pass;
                if (alt.getPassword().equals("")) {
                    pass = "§cCracked";
                } else {
                    pass = alt.getPassword().replaceAll(".", "*");
                }
                if (alt == this.selectedAlt) {
                    if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
                        Gui.drawRect(52.0f, y - this.lastOffset - 4, this.width - 52, y - this.lastOffset + 20,  Colors.getColor(145, 50));
                    } else if (this.isMouseOverAlt(par1, par2, y - this.lastOffset)) {
                        Gui.drawRect(52.0f, y - this.lastOffset - 4, this.width - 52, y - this.lastOffset + 20,  Colors.getColor(145, 50));
                    } else {
                        Gui.drawRect(52.0f, y - this.lastOffset - 4, this.width - 52, y - this.lastOffset + 20,  Colors.getColor(145, 50));
                    }
                } else if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
                    Gui.drawRect(52.0f, y - this.lastOffset - 4, this.width - 52, y - this.lastOffset + 20, -Colors.getColor(145, 50) );
                } else if (this.isMouseOverAlt(par1, par2, y - this.lastOffset)) {
                    Gui.drawRect(52.0f, y - this.lastOffset - 4, this.width - 52, y - this.lastOffset + 20,  Colors.getColor(145, 50));
                }
                fontRenderer.drawCenteredString( name, this.width / 2, y - this.lastOffset, -1, true);
                fontRenderer.drawCenteredString( pass, this.width / 2, y - this.lastOffset + 10, Colors.getColor(110), true);
                y += 26;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();

        super.drawScreen(par1, par2, par3);
        if (this.selectedAlt == null) {
            this.login.enabled = false;
            this.remove.enabled = false;
            this.rename.enabled = false;
        } else {
            this.login.enabled = true;
            this.remove.enabled = true;
            this.rename.enabled = true;
        }
        if (Keyboard.isKeyDown(200)) {
            this.offset -= 26;
        } else if (Keyboard.isKeyDown(208)) {
            this.offset += 26;
        }
        if (this.offset < 0) {
            this.offset = 0;
        }

    }



    @Override
    public void initGui() {
    	AltManager.registry.clear();
        try {
            Hypnotic.instance.getFileManager().getFile(Alts.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.buttonList.add(this.login = new AnimatedButton(1, this.width / 2 - 122, this.height - 48, 100, 20, "Login"));
        this.buttonList.add(this.remove = new AnimatedButton(2, this.width / 2 - 40, this.height - 24, 70, 20, "Remove"));
        this.buttonList.add(new AnimatedButton(3, this.width / 2 + 4 + 86, this.height - 48, 100, 20, "Add"));
        this.buttonList.add(new AnimatedButton(4, this.width / 2 - 16, this.height - 48, 100, 20, "Direct Login"));
        this.buttonList.add(new AnimatedButton(5, this.width / 2 - 122, this.height - 24, 78, 20, "Random"));
        this.buttonList.add(this.rename = new AnimatedButton(6, this.width / 2 + 38, this.height - 24, 70, 20, "Edit"));
        this.buttonList.add(new AnimatedButton(7, this.width / 2 - 190, this.height - 24, 60, 20, "Back"));
        this.buttonList.add(new AnimatedButton(8, this.width / 2 - 190, this.height - 48, 60, 20, "Reload"));

        this.buttonList.add(new AnimatedButton(9, this.width / 2 + 116, this.height - 24, 70, 20, "Altening"));
        this.buttonList.add(new AnimatedButton(10, this.width / 2 + 186, this.height - 24, 70, 20, "Import File"));
        this.login.enabled = false;
        this.remove.enabled = false;
        this.rename.enabled = false;
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        try {
            super.keyTyped(par1, par2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAltInArea(final int y) {
        return y - this.offset <= this.height - 50;
    }

    private boolean isMouseOverAlt(final int x, final int y, final int y1) {
        return x >= 52 && y >= y1 - 4 && x <= this.width - 52 && y <= y1 + 20 && x >= 0 && y >= 33 && x <= this.width && y <= this.height - 50;
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (this.offset < 0) {
            this.offset = 0;
        }
        int y = 38 - this.offset;
        for (final Alt alt : getAlts()) {
            if (isMouseOverAlt(par1, par2, y)) {
                if (alt == this.selectedAlt) {
                    (this.loginThread = new AltLoginThread(selectedAlt)).start();
                    return;
                }
                this.selectedAlt = alt;
            }
            y += 26;
        }
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Alt> getAlts() {
        List<Alt> altList = new ArrayList<>();
        for (final Alt alt : AltManager.registry) {

            altList.add(alt);
        }

        return altList;
    }
    
    @Override
    public void onGuiClosed() {
    	this.status = EnumChatFormatting.GRAY + "Idle...";
    }

    public void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(this.mc);
        final int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x * 1), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

}
