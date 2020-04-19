import React, { useEffect, useState } from 'react';
import { Container, Table } from 'react-bootstrap';
import styled from 'styled-components';
export const UserService = () => {
  const [servicesData, setServicesData] = useState([
    {
      serviceId: 0,
      carId: 0,
      brand: '',
      plates_number: '',
      serviceName: '',
      date: '',
      time: '',
      status: '',
    },
  ]);

  useEffect(() => {
    //GET FROM API ALL INF ABOUT USER SERVICES BY USER ID
    setServicesData([
      {
        serviceId: 0,
        carId: 0,
        brand: 'Mercedes',
        plates_number: 'THI66666',
        serviceName: 'Detailing felg',
        date: '01-01-2020',
        time: '13:00',
        status: 'Zakończono',
      },
      {
        serviceId: 1,
        carId: 0,
        brand: 'Mercedes',
        plates_number: 'THI66666',
        serviceName: 'Korekta lakieru',
        date: '02-01-2020',
        time: '11:00',
        status: 'Anulowano',
      },
      {
        serviceId: 2,
        carId: 0,
        brand: 'Mercedes',
        plates_number: 'THI66666',
        serviceName: 'Regeneracja kloszy samochodowych',
        date: '03-01-2020',
        time: '10:00',
        status: 'Zostało 15 min',
      },
      {
        serviceId: 3,
        carId: 0,
        brand: 'Mercedes',
        plates_number: 'THI66666',
        serviceName: 'Regeneracja kloszy samochodowych',
        date: '03-01-2021',
        time: '10:00',
        status: 'Rezerwacja',
      },
    ]);
  }, []);

  return (
    <Container>
      {servicesData.length > 0 && (
        <Table responsive hover size='sm' className='text-center'>
          <thead>
            <tr>
              <th>Marka</th>
              <th>Numer rejestracyjny</th>
              <th>Serwis</th>
              <th>Data</th>
              <th>Czas</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {servicesData.map((service) => (
              <tr key={service.serviceId}>
                <td>{service.brand}</td>
                <td>{service.plates_number}</td>
                <td>{service.serviceName}</td>
                <td>{service.date}</td>
                <td>{service.time}</td>
                <Td title={service.status}>{service.status}</Td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
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
