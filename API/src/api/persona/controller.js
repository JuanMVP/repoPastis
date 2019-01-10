import { success, notFound } from '../../services/response/'
import { Persona } from '.'

export const create = ({ bodymen: { body } }, res, next) =>
  Persona.create(body)
    .then((persona) => persona.view(true))
    .then(success(res, 201))
    .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Persona.count(query)
    .then(count => Persona.find(query, select, cursor)
      .then((personas) => ({
        count,
        rows: personas.map((persona) => persona.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Persona.findById(params.id)
    .then(notFound(res))
    .then((persona) => persona ? persona.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Persona.findById(params.id)
    .then(notFound(res))
    .then((persona) => persona ? Object.assign(persona, body).save() : null)
    .then((persona) => persona ? persona.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) =>
  Persona.findById(params.id)
    .then(notFound(res))
    .then((persona) => persona ? persona.remove() : null)
    .then(success(res, 204))
    .catch(next)
