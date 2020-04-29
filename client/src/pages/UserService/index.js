import React, { useEffect, useState } from 'react';
import { Container, Table } from 'react-bootstrap';
import styled from 'styled-components';
import { getAllUserServices } from '../../helpers/apiCommands';

export const UserService = () => {
  const [servicesData, setServicesData] = useState([]);

  useEffect(() => {
    getAllUserServices()
      .then((res) => {
        if (res.status === 200) setServicesData(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <Container>
      <Table responsive hover size='sm' className='text-center'>
        <thead>
          <tr>
            <th>Marka</th>
            <th>Numer rejestracyjny</th>
            <th>Serwis</th>
            <th>Data</th>
            <th>Godzina</th>
            <th>Status</th>
            <th>Płatność</th>
          </tr>
        </thead>
        <tbody>
          {servicesData.length > 0 &&
            servicesData.map((service) => (
              <tr key={service.id}>
                <td>{service.carDto.brand}</td>
                <td>{service.carDto.platesNumber}</td>
                <td>{service.servicesDto.name}</td>
                <td>{service.date}</td>
                <td>{service.time}:00</td>
                <Td title={service.status}>{service.status}</Td>
                <td>{service.paidStatus}</td>
              </tr>
            ))}
        </tbody>
      </Table>
    </Container>
  );
};

export const Td = styled.td`
  color: ${(props) =>
    props.title === 'Anulowano'
      ? 'red'
      : props.title === 'Zakończono'
      ? 'green'
      : props.title === 'Rezerwacja'
      ? 'blue'
      : 'orange'};
`;
