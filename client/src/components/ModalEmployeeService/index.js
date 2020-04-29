import React, { useEffect, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { putServiceStatusById } from '../../helpers/apiCommands';

export const StatusModal = ({
  show,
  onHide,
  serviceId,
  serviceData,
  setServiceData,
}) => {
  const [status, setStatus] = useState(null);
  const [error, setError] = useState('');

  const saveStatusChanges = async () => {
    if (status) {
      try {
        await putServiceStatusById(serviceId, status);
        serviceData.map((el) =>
          el.serviceId === serviceId ? (el.status = status) : el
        );
        setServiceData(serviceData);
        onHide();
      } catch (e) {
        if (e.response.status === 400)
          setError(
            'Nie można zmienić statusu z Zakończono lub Anulowano na inny'
          );
      }
    }
  };

  useEffect(() => {
    setError('');
  }, [status]);

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
          name='Rezerwacja'
          variant='outline-primary'
        >
          Rezerwacja
        </Button>
        <Button
          onClick={setStatusByMethod}
          name='Zostało 15 min'
          variant='outline-warning'
        >
          Zostało 15 min
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
        {error && <p>{error}</p>}
      </Modal.Footer>
    </Modal>
  );
};
