package model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

public class Equipe {
	private int id;
	private String nome;
	private String descricao;
	private byte[] foto;
	private String cargo;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public byte[] getFoto() {
		return foto;
	}
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public String getCargo() {
		return cargo;
	}
	
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public void setFoto(Blob blob) {
        try {
            if (blob != null) {
                InputStream inputStream = blob.getBinaryStream();
                this.foto = convertInputStreamToByteArray(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private byte[] convertInputStreamToByteArray(InputStream inputStream) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
