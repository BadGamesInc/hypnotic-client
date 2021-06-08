package badgamesinc.hypnotic.module.render.wings;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.render.BrightPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderWings extends ModelBase {

	private Minecraft mc;
	private ResourceLocation location;
	private ModelRenderer wing;
	private ModelRenderer wingTip;
	private boolean playerUsesFullHeight;

	public RenderWings()
	{
		this.mc = Minecraft.getMinecraft();
		this.location = new ResourceLocation("hypnotic/textures/wings.png");
		this.playerUsesFullHeight = true;

		// Set texture offsets.
		setTextureOffset("wing.bone", 0, 0);
		setTextureOffset("wing.skin", -10, 8);
		setTextureOffset("wingtip.bone", 0, 5);
		setTextureOffset("wingtip.skin", -10, 18);

		// Create wing model renderer.
		wing = new ModelRenderer(this, "wing");
		wing.setTextureSize(30, 30); // 300px / 10px
		wing.setRotationPoint(-2F, 0, 0);
		wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
		wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);

		// Create wing tip model renderer.
		wingTip = new ModelRenderer(this, "wingtip");
		wingTip.setTextureSize(30, 30); // 300px / 10px
		wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
		wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
		wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
		wing.addChild(wingTip); // Make the wingtip rotate around the wing.
	}
	
	public void onRenderPlayer(EntityPlayer player, float partialTick) {
		
		if (player.equals(mc.thePlayer) && !player.isInvisible()) // Should render wings onto this
																						// player?
		{
			renderWings(player, partialTick);
		}
	}

	private void renderWings(EntityPlayer player, float partialTicks) {
		double scale1 = 80;
		double scale = scale1 / 100D;
		double rotate = interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);

		GL11.glPushMatrix();
		GL11.glScaled(-scale, -scale, scale);
		GL11.glRotated(180 + rotate, 0, 1, 0); // Rotate the wings to be with the player.
		GL11.glTranslated(0, -(1.28) / scale, 0); // Move wings correct amount up.
		GL11.glTranslated(0, 0, (mc.thePlayer.getCurrentArmor(2) == null ? 0.1 : 0.17) / scale);

		if (player.isSneaking()) {
			GL11.glTranslated(0D, 0.125D / scale, 0D);
		}

		float[] colors = new float[] {0.8F, 0.8F, 0.8F};
		if (Hypnotic.instance.moduleManager.getModule(BrightPlayer.class).isEnabled())
			GL11.glColor3f(255, 255, 255);
		else
			GL11.glColor3f(colors[0], colors[1], colors[2]);
		mc.getTextureManager().bindTexture(location);

		for (int j = 0; j < 2; ++j) {
			GL11.glEnable(GL11.GL_CULL_FACE);
			float f11 = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;
			this.wing.rotateAngleX = (float) Math.toRadians(-80F) - (float) Math.cos((double) f11) * 0.2F;
			this.wing.rotateAngleY = (float) Math.toRadians(40F) + (float) Math.sin(f11) * 0.4F;
			this.wing.rotateAngleZ = (float) Math.toRadians(20F);
			this.wingTip.rotateAngleZ = -((float) (Math.sin((double) (f11 + 2.0F)) + 0.5D)) * 0.75F;
			this.wing.render(0.0625F);
			GL11.glScalef(-1.0F, 1.0F, 1.0F);

			if (j == 0) {
				GL11.glCullFace(1028);
			}
		}

		GL11.glCullFace(1029);
		GL11.glDisable(GL11.GL_CULL_FACE);
		if (!Hypnotic.instance.moduleManager.getModule(BrightPlayer.class).isEnabled())
			GL11.glColor3f(1, 1, 1);
		else
			GL11.glColor3f(255, 255, 255);
		GL11.glPopMatrix();
	}

	private float interpolate(float yaw1, float yaw2, float percent) {
		float f = (yaw1 + (yaw2 - yaw1) * percent) % 360;

		if (f < 0) {
			f += 360;
		}

		return f;
	}
	
	public static RenderWings renderWingsObject;
	
	public static RenderWings getWings() {
		
		if (renderWingsObject == null) {
			renderWingsObject = new RenderWings();
		}
		
		return renderWingsObject;
		
	}
	
}
