import { success, notFound } from '../../services/response/'
import { Medicamento } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Medicamento.create(body)
    .then((medicamento) => medicamento.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Medicamento.count(query)
    .then(count => Medicamento.find(query, select, cursor)
      .then((medicamentos) => ({
        count,
        rows: medicamentos.map((medicamento) => medicamento.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Medicamento.findById(params.id)
    .then(notFound(res))
    .then((medicamento) => medicamento ? medicamento.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Medicamento.findById(params.id)
    .then(notFound(res))
    .then((medicamento) => medicamento ? Object.assign(medicamento, body).save() : null)
    .then((medicamento) => medicamento ? medicamento.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Medicamento.findById(params.id)
    .then(notFound(res))
    .then((medicamento) => medicamento ? medicamento.remove() : null)
    .then(success(res, 204))
    .catch(next)
