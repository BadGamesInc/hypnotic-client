package net.minecraft.client.gui;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import badgamesinc.hypnotic.gui.AnimatedButton;
import badgamesinc.hypnotic.gui.altmanager.Alt;
import badgamesinc.hypnotic.gui.altmanager.AltLoginThread;
import badgamesinc.hypnotic.gui.altmanager.AltManager;
import badgamesinc.hypnotic.util.ServerUtil;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;

public class GuiDisconnected extends GuiScreen
{
	String username = "null";
    private AltLoginThread loginThread;
    private String reason;
    private IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    ServerData lastServer;

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp)
    {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
    }

    public ServerData getLastServer() {
        return this.lastServer;
     }

     public void setLastServer(ServerData lastServer) {
        this.lastServer = lastServer;
     }
     
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new AnimatedButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
        this.buttonList.add(new AnimatedButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 23, I18n.format("Reconnect", new Object[0])));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        } 
        else if (button.id == 1)
        {
        	mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, ServerUtil.lastLogin));
        }
    }
    
    private Session createSession(final String username, final String password) {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }
    
    private List<Alt> getAlts() {
        List<Alt> altList = new ArrayList<>();
        for (final Alt alt : AltManager.registry) {

            altList.add(alt);

        }
        return altList;
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.field_175353_i / 2;

        if (this.multilineMessage != null)
        {
            for (String s : this.multilineMessage)
            {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
