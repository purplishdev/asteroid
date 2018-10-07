package com.asteroid.game.asset.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;

public class PolygonLoader extends SynchronousAssetLoader<Polygon, PolygonLoader.PolygonParameters> {

    public PolygonLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Polygon load(AssetManager assetManager, String fileName, FileHandle file, PolygonParameters parameter) {
        try (BufferedReader reader = file.reader(256)) {
            String line = reader.readLine();
            if (line != null) {
                String[] polygonStrings = line.trim().split(",");
                float[] vertices = new float[polygonStrings.length];
                for (int i = 0, n = vertices.length; i < n; i++) {
                    vertices[i] = Float.parseFloat(polygonStrings[i]);
                }
                return new Polygon(vertices);
            }
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error reading polygon shape file: " + file, ex);
        }
        throw new GdxRuntimeException("Polygon shape not found: " + file);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, PolygonParameters parameter) {
        return null;
    }

    public static class PolygonParameters extends AssetLoaderParameters<Polygon> {
    }
}