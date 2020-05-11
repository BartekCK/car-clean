import React, { useEffect, useState } from 'react';
import { Button, Container, Table } from 'react-bootstrap';
import styled from 'styled-components';
import { getAllUserServices } from '../../helpers/apiCommands';
import { Redirect } from 'react-router-dom';
import { ORDER_SERVICE } from '../../helpers/orderActions';

export const UserService = () => {
  const [servicesData, setServicesData] = useState([]);
  const [redirectLink, setRedirectLink] = useState(null);

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
            <th>Zapłać</th>
          </tr>
        </thead>
        <tbody>
          {servicesData.length > 0 &&
            servicesData.map((service) => (
              <tr key={service.id}>
                {service.carDto ? (
                  <>
                    <td>{service.carDto.brand}</td>
                    <td>{service.carDto.platesNumber}</td>
                  </>
                ) : (
                  <>
                    <td>-</td>
                    <td>-</td>
                  </>
                )}
                <td>{service.servicesDto.name}</td>
                <td>{service.date}</td>
                <td>{service.time}:00</td>
                <Td title={service.status}>{service.status}</Td>
                <td>{service.paidStatus}</td>
                <td>
                  <PaidButton
                    service={service}
                    setRedirectLink={setRedirectLink}
                  />
                </td>
              </tr>
            ))}
        </tbody>
      </Table>
      {redirectLink && <Redirect to={redirectLink} />}
    </Container>
  );
};

const PaidButton = ({ service, setRedirectLink }) => {
  if (service.status === 'Anulowano') return null;
  else if (service.paidStatus === 'Nie zapłacono')
    return (
      <Button
        variant='outline-secondary'
        onClick={() =>
          setRedirectLink(`/payments/${ORDER_SERVICE}/${service.id}`)
        }
      >
        Zapłać
      </Button>
    );
  else return null;
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
