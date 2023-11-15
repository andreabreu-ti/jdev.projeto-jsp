package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelTelefone implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String numero;

	/*
	 * Relacionamento entre tabelas
	 */
	private ModelLogin usuario_pai_id;
	private ModelLogin usuario_cadastro_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public ModelLogin getUsuario_pai_id() {
		return usuario_pai_id;
	}

	public void setUsuario_pai_id(ModelLogin usuario_pai_id) {
		this.usuario_pai_id = usuario_pai_id;
	}

	public ModelLogin getUsuario_cadastro_id() {
		return usuario_cadastro_id;
	}

	public void setUsuario_cadastro_id(ModelLogin usuario_cadastro_id) {
		this.usuario_cadastro_id = usuario_cadastro_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelTelefone other = (ModelTelefone) obj;
		return Objects.equals(id, other.id);
	}

}
