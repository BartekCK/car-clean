import React, { useState } from 'react';
import { Button, Modal } from 'react-bootstrap';

export const StatusModal = ({
  show,
  onHide,
  serviceId,
  serviceData,
  setServiceData,
}) => {
  const [status, setStatus] = useState(null);

  const saveStatusChanges = async () => {
    if (status) {
      //AWAIT POST TO API FOR CHANGE STATUS BY SERVICE ID
      serviceData.map((el) =>
        el.serviceId === serviceId ? (el.status = status) : el
      );
      setServiceData(serviceData);
      onHide();
    }
  };

  const setStatusByMethod = async (e) => {
    await setStatus(e.target.name);
  };

  return (
    <Modal show={show}>
      <Modal.Header>
        <Modal.Title>Wybierz status</Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Button
          onClick={setStatusByMethod}
          name='Zakończono'
          variant='outline-success'
        >
          Zakończono
        </Button>
        <Button
          onClick={setStatusByMethod}
          name='Zostalo 15 min'
          variant='outline-warning'
        >
          Zostalo 15 min
        </Button>
        <Button
          onClick={setStatusByMethod}
          name='Anulowano'
          variant='outline-danger'
        >
          Anulowano
        </Button>
      </Modal.Body>

      <Modal.Footer>
        <Button onClick={onHide} variant='danger'>
          Anuluj
        </Button>
        <Button onClick={saveStatusChanges} variant='success'>
          Zapisz zmiany
        </Button>
      </Modal.Footer>
    </Modal>
  );
};
