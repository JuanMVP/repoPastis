import { success, notFound } from '../../services/response/'
import { Tratamiento } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Tratamiento.create(body)
    .then((tratamiento) => tratamiento.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Tratamiento.count(query)
    .then(count => Tratamiento.find(query, select, cursor)
      .then((tratamientos) => ({
        count,
        rows: tratamientos.map((tratamiento) => tratamiento.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Tratamiento.findById(params.id)
    .then(notFound(res))
    .then((tratamiento) => tratamiento ? tratamiento.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Tratamiento.findById(params.id)
    .then(notFound(res))
    .then((tratamiento) => tratamiento ? Object.assign(tratamiento, body).save() : null)
    .then((tratamiento) => tratamiento ? tratamiento.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Tratamiento.findById(params.id)
    .then(notFound(res))
    .then((tratamiento) => tratamiento ? tratamiento.remove() : null)
    .then(success(res, 204))
    .catch(next)
