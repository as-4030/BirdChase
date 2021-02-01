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

public class Core extends ApplicationAdapter {
	private float timePassed = 0.1f;
	private SpriteBatch batch;

	private OrthographicCamera camera;
	private FillViewport viewport;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private MapProperties mapProperties;

	private Dog dog;

	private Bird bird;

	@Override
	public void create() {
		float WORLD_HEIGHT = 20;
		float WORLD_WIDTH = 20;

		this.batch = new SpriteBatch();

		this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		this.camera.translate(new Vector2(20, 20));

		this.viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, this.camera);
		this.viewport.apply();

		this.map = new TmxMapLoader().load("MagicMap.tmx"); // 1280x1280 size; 80x80 meters.
		this.renderer = new OrthogonalTiledMapRenderer(this.map, (1/16f));

		this.dog = new Dog(36, 36);
		this.bird = new Bird(40, 40);

		this.camera.position.x = this.dog.x;
		this.camera.position.y = this.dog.y;

		this.mapProperties = this.map.getProperties();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.timePassed += Gdx.graphics.getDeltaTime();
		this.batch.setProjectionMatrix(this.camera.combined);

		this.camera.update();
		this.camera.position.x += (this.dog.x + 2 - this.camera.position.x) * 2.2 * (1/60f);
		this.camera.position.y += (this.dog.y + 2 - this.camera.position.y) * 3.4 * (1/60f);

		this.dog.boundingBox.setPosition(this.dog.x, this.dog.y);

		this.batch.begin();

		checkCollisions();

		this.renderer.setView(this.camera);
		this.renderer.render();
		this.batch.draw(this.bird.animation.getKeyFrame(this.timePassed, true), this.bird.x, this.bird.y, 2, 1.5f);

		int moveInput = movementInput();
		physicalMovement(moveInput);
		movingAround(moveInput);

		this.batch.end();

		keepCameraInBounds();
	}

	public void checkCollisions() {
		// Dog can be underneath bird and detect a collision, but it can be touching its wings and not.
		// Says that x value of box needs to be a bit bigger, and y value needs to be a bit smaller.
		// This is all from the left / bottom though.
		if (this.bird.boundingBox.overlaps(this.dog.boundingBox)) {
			System.out.println("Touching! :D");
		}
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
			this.dog.y = this.dog.y + 0.2f;
		} else if (inputNum == 2) { // Down.
			this.dog.y = this.dog.y - 0.2f;
		} else if (inputNum == 3) { // Left.
			this.dog.x = this.dog.x - 0.2f;
		} else if (inputNum == 4) { // Right.
			this.dog.x = this.dog.x + 0.2f;
		}
	}

	public void movingAround(int moveInput) {
		if (moveInput == 1 && (this.dog.lookingRight)) { // Going up after looking right.
			this.batch.draw(this.dog.animation.getKeyFrame(this.timePassed, true), this.dog.x + 2, this.dog.y, -1, 1);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = true;
			this.dog.standingStill = true;
		} else if (moveInput == 1) { // Going up after looking left.
			this.batch.draw(this.dog.animation.getKeyFrame(this.timePassed, true), this.dog.x + 1, this.dog.y, 1, 1);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = true;
			this.dog.standingStill = true;
		} else if (moveInput == 2 && (this.dog.lookingRight)) { // Going down after looking right.
			this.batch.draw(this.dog.animation.getKeyFrame(this.timePassed, true), this.dog.x + 2, this.dog.y, -1, 1);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = true;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
		} else if (moveInput == 2) { // Going down after looking left.
			this.batch.draw(this.dog.animation.getKeyFrame(this.timePassed, true), this.dog.x + 1, this.dog.y, 1, 1);
			this.dog.standingStill = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = true;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
		} else if (moveInput == 3) { // Going left.
			this.batch.draw(this.dog.animation.getKeyFrame(this.timePassed, true), this.dog.x + 1, this.dog.y, 1, 1);
			this.dog.standingStill = false;
			this.dog.lookingRight = false;
			this.dog.goingRight = false;
			this.dog.goingLeft = true;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
		} else if (moveInput == 4) { // Going right.
			this.batch.draw(this.dog.animation.getKeyFrame(this.timePassed, true), this.dog.x + 2, this.dog.y, -1, 1);
			this.dog.standingStill = false;
			this.dog.lookingRight = true;
			this.dog.goingRight = true;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
			this.dog.standingStill = true;
		} else {
			if (this.dog.lookingRight) { // Standing still after looking right.
				this.batch.draw(this.dog.animation.getKeyFrame(1, false), this.dog.x + 2, this.dog.y, -1, 1);
				this.dog.standingStill = true;
			} else { // Standing still after looking left.
				this.batch.draw(this.dog.animation.getKeyFrame(1, false), this.dog.x + 1, this.dog.y, 1, 1);
				this.dog.standingStill = true;
				this.dog.lookingRight = false;
			}
			this.dog.goingRight = false;
			this.dog.goingLeft = false;
			this.dog.goingDown = false;
			this.dog.goingUp = false;
		}
	}
	
	@Override
	public void dispose() {
		this.batch.dispose();
		this.map.dispose();
		this.renderer.dispose();
		this.dog.animationAtlas.dispose();
		this.bird.animationAtlas.dispose();
	}

	@Override
	public void resize(int width, int height) {
		this.viewport.update(width, height);
	}

	public void keepCameraInBounds() {
		int mapLeft = 0;

		int mapRight = this.mapProperties.get("width", Integer.class);

		int mapBottom = 0;

		int mapTop = this.mapProperties.get("height", Integer.class);

		float cameraHalfWidth = this.camera.viewportWidth * .5f;
		float cameraHalfHeight = this.camera.viewportHeight * .5f;

		float cameraLeft = this.camera.position.x - cameraHalfWidth;
		float cameraRight = this.camera.position.x + cameraHalfWidth;
		float cameraBottom = this.camera.position.y - cameraHalfHeight;
		float cameraTop = this.camera.position.y + cameraHalfHeight;

		if (this.mapProperties.get("width", Integer.class) < this.camera.viewportWidth) {
			this.camera.position.x = mapRight / 2f;
		} else if (cameraLeft <= mapLeft) {
			this.camera.position.x = mapLeft + cameraHalfWidth;
		} else if (cameraRight >= mapRight) {
			this.camera.position.x = mapRight - cameraHalfWidth;
		}

		if (this.mapProperties.get("height", Integer.class) < this.camera.viewportHeight) {
			this.camera.position.y = mapTop / 2f;
		} else if (cameraBottom <= mapBottom) {
			this.camera.position.y = mapBottom + cameraHalfHeight;
		} else if (cameraTop >= mapTop) {
			this.camera.position.y = mapTop - cameraHalfHeight;
		}
	}

}