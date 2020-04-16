import React, { useEffect, useState } from 'react';
import { Col, Container, Row, Table } from 'react-bootstrap';
import { Calendar } from 'react-calendar';
import { Td } from '../UserService';
import { StatusModal } from '../../components/ModalEmployeeService';

export const EmployeeService = () => {
  const [chosenDate, setDate] = useState(new Date());
  const [serviceData, setServiceData] = useState([
    {
      serviceId: 0,
      brand: '',
      plates_number: '',
      serviceName: '',
      date: '',
      time: '',
      status: '',
    },
  ]);

  const [isModalVisible, setModalVisible] = useState(false);
  const [chosenId, setChosenId] = useState();

  const changeDate = (chosenDate) => {
    //POST TO API THIS DATE AND GET FREE HOURS THIS DAY
    setDate(chosenDate);
  };

  useEffect(() => {
    //GET FROM API ALL SERVICES THIS DAY chosenDate
    console.log(chosenDate);
    //setServiceData by DAY
    setServiceData([
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
    ]);
  }, [chosenDate]);

  return (
    <Container className='my-3'>
      <Row className='flex-column '>
        <Col className='my-2'>
          <Calendar
            className='mx-auto'
            onChange={changeDate}
            value={chosenDate}
          />
        </Col>
        <Col>
          {serviceData.length > 0 && (
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
                {serviceData.map((service) => (
                  <tr
                    onClick={() => {
                      setChosenId(service.serviceId);
                      setModalVisible(true);
                    }}
                    key={service.serviceId}
                  >
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
        </Col>
      </Row>
      <StatusModal
        show={isModalVisible}
        onHide={() => setModalVisible(false)}
        serviceId={chosenId}
        serviceData={serviceData}
        setServiceData={setServiceData}
      />
    </Container>
  );
};
