import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Persona, { schema } from './model'

const router = new Router()
const { nombre, edad, fecha_nacimiento, discapacidad } = schema.tree

/**
 * @api {post} /personas Create persona
 * @apiName CreatePersona
 * @apiGroup Persona
 * @apiParam nombre Persona's nombre.
 * @apiParam edad Persona's edad.
 * @apiParam fecha_nacimiento Persona's fecha_nacimiento.
 * @apiParam discapacidad Persona's discapacidad.
 * @apiSuccess {Object} persona Persona's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Persona not found.
 */
router.post('/',
  body({ nombre, edad, fecha_nacimiento, discapacidad }),
  create)

/**
 * @api {get} /personas Retrieve personas
 * @apiName RetrievePersonas
 * @apiGroup Persona
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of personas.
 * @apiSuccess {Object[]} rows List of personas.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 */
router.get('/',
  query(),
  index)

/**
 * @api {get} /personas/:id Retrieve persona
 * @apiName RetrievePersona
 * @apiGroup Persona
 * @apiSuccess {Object} persona Persona's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Persona not found.
 */
router.get('/:id',
  show)

/**
 * @api {put} /personas/:id Update persona
 * @apiName UpdatePersona
 * @apiGroup Persona
 * @apiParam nombre Persona's nombre.
 * @apiParam edad Persona's edad.
 * @apiParam fecha_nacimiento Persona's fecha_nacimiento.
 * @apiParam discapacidad Persona's discapacidad.
 * @apiSuccess {Object} persona Persona's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Persona not found.
 */
router.put('/:id',
  body({ nombre, edad, fecha_nacimiento, discapacidad }),
  update)

/**
 * @api {delete} /personas/:id Delete persona
 * @apiName DeletePersona
 * @apiGroup Persona
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Persona not found.
 */
router.delete('/:id',
  destroy)

export default router
