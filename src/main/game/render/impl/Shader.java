package main.game.render.impl;

import main.game.render.IShader;
import main.game.render.RenderManager;
import main.game.util.FileUtil;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader implements IShader {

    public static final String FILE_EXT_FRAGMENT = ".frag";
    public static final String FILE_EXT_VERTEX = ".vert";

    private final int programID;

    public Shader(String shaderName) {
        programID = RenderManager.getNewProgramID();
        if (shaderName != null) {
            GL20.glAttachShader(programID, createShader(shaderName + FILE_EXT_VERTEX, GL20.GL_VERTEX_SHADER));
            GL20.glAttachShader(programID, createShader(shaderName + FILE_EXT_FRAGMENT, GL20.GL_FRAGMENT_SHADER));
        }

        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Could not link shader. Reason: " + GL20.glGetProgramInfoLog(programID, GL20.glGetProgrami(programID, GL20.GL_INFO_LOG_LENGTH)));
        }

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Could not validate shader. Reason: " + GL20.glGetProgramInfoLog(programID, GL20.glGetProgrami(programID, GL20.GL_INFO_LOG_LENGTH)));
        }
    }

    public Shader(String vertexShader, String fragmentShader) {
        programID = RenderManager.getNewProgramID();
        if (vertexShader != null) {
            GL20.glAttachShader(programID, createShaderFromCode(vertexShader, GL20.GL_VERTEX_SHADER));
        }
        if (fragmentShader != null) {
            GL20.glAttachShader(programID, createShaderFromCode(fragmentShader, GL20.GL_FRAGMENT_SHADER));
        }

        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Could not link shader. Reason: " + GL20.glGetProgramInfoLog(programID, GL20.glGetProgrami(programID, GL20.GL_INFO_LOG_LENGTH)));
        }

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Could not validate shader. Reason: " + GL20.glGetProgramInfoLog(programID, GL20.glGetProgrami(programID, GL20.GL_INFO_LOG_LENGTH)));
        }
    }

    private int createShader(String string, int glVertexShader) {
        return createShaderFromCode(FileUtil.readShaderFileAsString(string), glVertexShader);
    }

    private int createShaderFromCode(String code, int glVertexShader) {
        int shaderID = GL20.glCreateShader(glVertexShader);
        if (shaderID == 0) {
            throw new RuntimeException("could not created shader of type " + glVertexShader + " for file " + code + ". " + GL20.glGetProgramInfoLog(programID, GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH)));
        }
        GL20.glShaderSource(shaderID, code);
        GL20.glCompileShader(shaderID);
        int shaderStatus = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
        if (shaderStatus == GL11.GL_FALSE) {
            throw new RuntimeException("Compilation error for shader '" + code + "'. Reason: " + GL20.glGetShaderInfoLog(shaderID, GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH)));
        }
        return shaderID;
    }

    @Override
    public int getProgramID() {
        return programID;
    }

}
