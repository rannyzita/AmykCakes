package model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

public class Produto extends BaseEntity{
	private String nome;
	private String descricao;
	private double preco;
	private byte[] foto;
	private int estoque;
	private Pedido Pedido_id; 
	
	// construtor pra qnd n tiver nd no arg
	public Produto () {
		
	}
	// construtor pra qnd tiver algo de arg
	public Produto(int id, String nome, byte[] foto, String descricao, double preco, int estoque) {
        super.setId(id); // Chama o construtor da classe BaseEntity para inicializar o ID
        this.nome = nome;
        this.foto = foto;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
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
	
	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public byte[] getFoto() {
		return foto;
	}
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public int getEstoque() {
		return estoque;
	}
	
	public void setEstoque(int estoque) {
		this.estoque = estoque;
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

	public String getRescricao() {
		return null;
	}

	public Pedido getPedido_id() {
		return Pedido_id;
	}

	public void setPedido_id(Pedido pedido_id) {
		Pedido_id = pedido_id;
	}

}
