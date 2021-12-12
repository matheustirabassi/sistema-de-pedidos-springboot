package com.matheustirabassi.cursomc.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;

import com.matheustirabassi.cursomc.domain.Login;
import com.matheustirabassi.cursomc.domain.enums.StatusPermissao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {
	private Integer id;
	@Column(unique = true)
	private String user;
	private String password;
	private Integer statusPermissao;
	private Integer clienteId;

	public LoginDto(Login login) {
		id = login.getId();
		user = login.getUser();
		password = login.getPassword();
		statusPermissao = login.getCliente().getStatusPermissao().getCod();
		clienteId = login.getCliente().getId();
	}

	public static List<LoginDto> convertList(List<Login> logins) {
		return logins.stream().map(LoginDto::new).collect(Collectors.toList());
	}
	
	public StatusPermissao getStatusPermissao() {
		return StatusPermissao.toEnum(statusPermissao);
	}

	public void setStatusPermissao(StatusPermissao statusPermissao) {
		this.statusPermissao = statusPermissao.getCod();
	}
}
