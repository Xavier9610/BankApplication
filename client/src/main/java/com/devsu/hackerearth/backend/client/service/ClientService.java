package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;

public interface ClientService {

	public List<ClientDto> getAll();
	public ClientDto getById(Long id);
	public ClientDto create(ClientDto clientDto);
	public ClientDto update(ClientDto clientDto);
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto);
	public void deleteById(Long id);
}
