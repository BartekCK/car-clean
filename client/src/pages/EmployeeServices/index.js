import React, { useEffect, useState } from 'react';
import { Col, Container, Row, Table } from 'react-bootstrap';
import { Calendar } from 'react-calendar';
import { Td } from '../UserService';
import { StatusModal } from '../../components/ModalEmployeeService';
import { getAllEmployeeServicesByDay } from '../../helpers/apiCommands';
import { formatDate } from '../../helpers/time';

export const EmployeeService = () => {
  const [chosenDate, setDate] = useState(new Date(Date.now()));
  const [serviceData, setServiceData] = useState([]);

  const [isModalVisible, setModalVisible] = useState(false);
  const [chosenId, setChosenId] = useState();

  const changeDate = (chosenDate) => {
    setDate(chosenDate);
  };

  useEffect(() => {
    getAllEmployeeServicesByDay(new Date(formatDate(chosenDate)))
      .then((res) => {
        if (res.status === 200) setServiceData(res.data);
      })
      .catch((err) => console.log(err));
  }, [chosenDate, isModalVisible]);

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
              {serviceData.length > 0 &&
                serviceData.map((service) => (
                  <tr
                    onClick={() => {
                      setChosenId(service.id);
                      setModalVisible(true);
                    }}
                    key={service.id}
                  >
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
