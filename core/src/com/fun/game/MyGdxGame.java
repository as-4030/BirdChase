package com.fun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MyGdxGame extends ApplicationAdapter {
	private float timePassed = 0.1f;
	private SpriteBatch batch;

	private OrthographicCamera camera;
	private FillViewport viewport;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private MapProperties mapProperties;

	private Dog dog;
	
	@Override
	public void create() {
		float WORLD_HEIGHT = 60;
		float WORLD_WIDTH = 60;

		this.batch = new SpriteBatch();

		this.camera = new OrthographicCamera();
		this.camera.translate(new Vector2(20, 20));

		this.viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, this.camera);
		this.viewport.apply();

		this.map = new TmxMapLoader().load("MagicMap.tmx"); // 1280x1280 size.
		this.renderer = new OrthogonalTiledMapRenderer(this.map);

		this.dog = new Dog(36, 36);

		this.camera.position.x = this.dog.dogX;
		this.camera.position.y = this.dog.dogY;

		this.mapProperties = this.map.getProperties();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.timePassed += Gdx.graphics.getDeltaTime();
		this.batch.setProjectionMatrix(this.camera.combined);

		this.camera.update();
		this.camera.position.x += (this.dog.dogX + 2 - this.camera.position.x) * 2.2 * (1/60f);
		this.camera.position.y += (this.dog.dogY + 2 - this.camera.position.y) * 3.4 * (1/60f);

		this.keepCameraInBounds();
		this.batch.begin();

		this.renderer.setView(this.camera);
		this.renderer.render();

		int moveInput = movementInput();
		physicalMovement(moveInput);
		movingAround(moveInput);

		this.batch.end();
	}

	public int movementInput() {
		boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
		boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		if (upPressed) {
			return 1;
		} else if (downPressed) {
			return 2;
		} else if (leftPressed) {
			return 3;
		} else if (rightPressed) {
			return 4;
		}

		return 0;
	}

	public void physicalMovement(int inputNum) {
		if (inputNum == 1) { // Up.
			this.dog.dogY++;
		} else if (inputNum == 2) { // Down.
			this.dog.dogY--;
		} else if (inputNum == 3) { // Left.
			this.dog.dogX--;
		} else if (inputNum == 4) { // Right.
			this.dog.dogX++;
		}
	}

	public void movingAround(int moveInput) {
		if (moveInput == 1 && (this.dog.lookingRight)) { // Going up after looking right.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(this.timePassed, true), this.dog.dogX + 2, this.dog.dogY, -2, 2);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = true;
			this.dog.standingStill = true;
//			this.directionChecker();
		} else if (moveInput == 1) { // Going up after looking left.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(this.timePassed, true), this.dog.dogX, this.dog.dogY, 2, 2);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = true;
			this.dog.standingStill = true;
//			this.directionChecker();
		} else if (moveInput == 2 && (this.dog.lookingRight)) { // Going down after looking right.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(this.timePassed, true), this.dog.dogX + 2, this.dog.dogY, -2, 2);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = true;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
//			this.directionChecker();
		} else if (moveInput == 2) { // Going down after looking left.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(this.timePassed, true), this.dog.dogX, this.dog.dogY, 2, 2);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = true;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
//			this.directionChecker();
		} else if (moveInput == 3) { // Going left.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(this.timePassed, true), this.dog.dogX, this.dog.dogY, 2, 2);
			this.dog.standingStill = false;
			this.dog.lookingRight = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = true;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
//			this.directionChecker();
		} else if (moveInput == 4) { // Going right.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(this.timePassed, true), this.dog.dogX + 2, this.dog.dogY, -2, 2);
			this.dog.standingStill = false;
			this.dog.lookingRight = true;
			this.dog.goingRight = true;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
//			this.directionChecker();
		} else if (this.dog.lookingRight) { // Standing still after looking right.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(1, false), this.dog.dogX + 2, this.dog.dogY, -2, 2);
			this.dog.standingStill = true;
//			this.directionChecker();
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
		} else { // Standing still after looking left.
			this.batch.draw(this.dog.dogAnimation.getKeyFrame(1, false), this.dog.dogX, this.dog.dogY, 2, 2);
			this.dog.standingStill = true;
//			this.directionChecker();
			this.dog.lookingRight = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
		}
	}

//	public void directionChecker() {
//		if (this.dog.standingStill == true) {
//			if (this.dog.goingRight == true) {
//				this.dog.dogX++;
//			} else if (this.dog.goingLeft == true) {
//				this.dog.dogX--;
//			} else if (this.dog.goingUp == true) {
//				this.dog.dogY++;
//			} else if (this.dog.goingDown == true) {
//				this.dog.dogY--;
//			}
//		}
//	}
	
	@Override
	public void dispose() {
		this.batch.dispose();
		this.map.dispose();
		this.renderer.dispose();
		this.dog.dogAtlas.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.viewport.update(width, height);
	}

	public void keepCameraInBounds() {
		int mapLeft = 0;

//		int mapRight = 0 + this.mapProperties.get("width", Integer.class);
		int mapRight = 1280;

		int mapBottom = 0;

//		int mapTop = 0 + this.mapProperties.get("height", Integer.class);
		int mapTop = 1280;

		float cameraHalfWidth = this.camera.viewportWidth * .5f;
		float cameraHalfHeight = this.camera.viewportHeight * .5f;

		float cameraLeft = this.camera.position.x - cameraHalfWidth;
		float cameraRight = this.camera.position.x + cameraHalfWidth;
		float cameraBottom = this.camera.position.y - cameraHalfHeight;
		float cameraTop = this.camera.position.y + cameraHalfHeight;

		if (this.mapProperties.get("width", Integer.class) < this.camera.viewportWidth) {
			this.camera.position.x = mapRight / 2;
		} else if (cameraLeft <= mapLeft) {
			this.camera.position.x = mapLeft + cameraHalfWidth;
		} else if (cameraRight >= mapRight) {
			this.camera.position.x = mapRight - cameraHalfWidth;
		}

		if (this.mapProperties.get("height", Integer.class) < this.camera.viewportHeight) {
			this.camera.position.y = mapTop / 2;
		} else if (cameraBottom <= mapBottom) {
			this.camera.position.y = mapBottom + cameraHalfHeight;
		} else if (cameraTop >= mapTop) {
			this.camera.position.y = mapTop - cameraHalfHeight;
		}
	}

}